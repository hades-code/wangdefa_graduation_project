package org.lhq.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileStatus;
import org.lhq.common.Chunk;
import org.lhq.entity.User;
import org.lhq.entity.UserFile;
import org.lhq.service.FileService;
import org.lhq.service.UserFileService;
import org.lhq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hades
 */
@RestController
@RequestMapping("tran")
@Slf4j
@Api(tags = "文件上传下载接口")
public class FileController {
	@Autowired
	FileService fileService;
	@Autowired
	UserFileService userFileService;
	@Autowired
	UserService userService;

	private static final String TEMPPATH = "/temp";
	/**
	 * 分片上传
	 * @param chunk
	 */
	@PostMapping("/chunkupload")
	public Map chunkUpload(Chunk chunk) throws Exception {
		MultipartFile file = chunk.getFile();
		// 临时文件路径，文件的MD5值为文件名
		String temPath = TEMPPATH + "/" + chunk.getIdentifier();
		//创建临时文件夹
		this.fileService.mkdir(temPath);

		log.info("分片信息{}",chunk);
		if (chunk.getChunkNumber().length() == 1) {
			chunk.setChunkNumber("0000"+chunk.getChunkNumber());
		}
		if (chunk.getChunkNumber().length() == 2) {
			chunk.setChunkNumber("000"+chunk.getChunkNumber());
		}
		if (chunk.getChunkNumber().length() == 3) {
			chunk.setChunkNumber("00"+chunk.getChunkNumber());
		}
		if (chunk.getChunkNumber().length() == 4) {
			chunk.setChunkNumber("0"+chunk.getChunkNumber());
		}
		String chunkName =temPath + "/" + chunk.getIdentifier()+"_"+ chunk.getChunkNumber();

		// 获取写入流
		InputStream is = file.getInputStream();
		this.fileService.upload(is,chunkName);
		// 上传成功则返回需要合并
		Map map = new HashMap();
		map.put("needMerge",true);
		return  map;
	}
	@GetMapping("checkChunk")
	public ResponseEntity checkChunk (Chunk chunk){
		//获取当前上传块的md5值
		String identifier = chunk.getIdentifier();
		UserFile fileByMd5 = userFileService.getUserFileDao().getUserFileByMd5(identifier);
		if (fileByMd5 != null){
			log.info("文件已存在,不需要再上传");
		}
		return ResponseEntity.ok("上传完成");
	}
	@GetMapping("md5")
	public ResponseEntity checkMD5(String md5,double fileSize){
		if (StrUtil.isEmpty(md5)){
			log.info("md5为空,查找错误");
		}
		UserFile userFileByMd5 = userFileService.getUserFileDao().getUserFileByMd5(md5);
		if (userFileByMd5 != null && userFileByMd5.getFileSize() == fileSize){
			log.info("文件存在，发动秒传");
		}
		log.info("文件不存在可以上传");
		return ResponseEntity.ok("检验完毕");
	}
	@PostMapping("mergeFile")
	public ResponseEntity mergeFile(String md5,
									String fileName,
									String totalSize,
									Long userId,
									Long dirId) throws Exception {
		// 获取临时文件的路径
		FileStatus[] fileStatuses = this.fileService.tempFile(TEMPPATH + "/" + md5);
		User userInfo = userService.getUserInfo(userId);
		Date date= new Date();
		if (userInfo == null){
			log.error("查无此人");
			return ResponseEntity.ok("上传失败,未找到上传用户");
		}
		// 合并文件
		String dirPath = "/"+userInfo.getUsername() + DateUtil.format(date,"yyyy-MM-dd HH:mm:ss");
		this.fileService.mkdir(dirPath);
		String filePath =dirPath+"/"+fileName;
		this.fileService.mergeFile(fileStatuses,filePath);
		// 检查MD5是否一致
		String chechMD5 = this.fileService.chechMD5(filePath);
		if (StrUtil.equals(chechMD5,md5)){
			UserFile userFile = new UserFile();
			int index = fileName.lastIndexOf(".");
			String type = null;
			if (index != -1){
				type = fileName.substring(index+1);
				fileName = fileName.substring(0,index);
			}
			userFile.setFileName(fileName);
			userFile.setDirectoryId(dirId);
			userFile.setCreateTime(date);
			userFile.setFileSize(Double.valueOf(totalSize));
			userFile.setMd5(md5);
			userFile.setFileType(type);
			userFile.setUserId(userId);
			userFile.setModifyTime(date);
			// 保存文件
			this.userFileService.getUserFileDao().insert(userFile);
			// 更新用户信息
			userInfo.setUsedStorageSize(userFile.getFileSize()+userInfo.getUsedStorageSize());
			this.userService.updateUserInfoById(userInfo);
			// 合并完成删除文件
			this.fileService.rm(TEMPPATH + "/" +md5);
			return ResponseEntity.ok("上传成功,文件合并成功");
		}
		return ResponseEntity.ok("文件合并失败");
	}
	@GetMapping("download")
	public ResponseEntity fileDownload(HttpServletRequest request,HttpServletResponse response,UserFile userFile){
		if (userFile == null||userFile.getId() < 0){
			log.error("下载失败");
			return ResponseEntity.ok("下载失败");
		}
        if (StrUtil.equals("dir", userFile.getFileType())) {
			UserFile downloadFile = this.userFileService.getUserFileDao().selectById(userFile.getId());
			String fileName;
			if (StrUtil.isNotEmpty(downloadFile.getFileType())){
				fileName = downloadFile.getFileName()+"."+downloadFile.getFileType();
			}else {
				fileName = downloadFile.getFileName();
			}
			try {
				response.setHeader("Content-Disposition", "attachment;fileName=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
				this.fileService.download(downloadFile.getFilePath(),response.getOutputStream());
			} catch (Exception e) {
				log.error("发生了不可描述的错误:",e);
			}
		}
        return null;
    }
	@GetMapping
	public void multipleDownload(){}
}
