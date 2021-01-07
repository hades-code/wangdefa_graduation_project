package org.lhq.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileStatus;
import org.lhq.annotation.JsonParam;
import org.lhq.common.ActionType;
import org.lhq.entity.vo.Chunk;
import org.lhq.entity.vo.Item;
import org.lhq.entity.vo.ResultVO;
import org.lhq.entity.Directory;
import org.lhq.entity.User;
import org.lhq.entity.UserFile;
import org.lhq.exception.ProjectException;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.FileService;
import org.lhq.service.UserFileService;
import org.lhq.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author hades
 */
@RestController
@RequestMapping("tran")
@Slf4j
@Api(tags = "文件上传下载接口")
public class FileController {
	@Resource
	private FileService fileService;
	@Resource
	private UserFileService userFileService;
	@Resource
	private UserService userService;
	@Resource
	private DirectorySerivce directorySerivce;

	private static final String TEMP_PATH = "/temp";

	/**
	 * 分片上传
	 *
	 * @param chunk
	 */
	@PostMapping("/chunkupload")
	public Map chunkUpload(Chunk chunk) throws Exception {
		MultipartFile file = chunk.getFile();
		// 临时文件路径，文件的MD5值为文件名
		String temPath = TEMP_PATH + "/" + chunk.getIdentifier();
		//创建临时文件夹
		this.fileService.mkdir(temPath);

		log.info("分片信息{}", chunk);
		if (chunk.getChunkNumber().length() == 1) {
			chunk.setChunkNumber("0000" + chunk.getChunkNumber());
		}
		if (chunk.getChunkNumber().length() == 2) {
			chunk.setChunkNumber("000" + chunk.getChunkNumber());
		}
		if (chunk.getChunkNumber().length() == 3) {
			chunk.setChunkNumber("00" + chunk.getChunkNumber());
		}
		if (chunk.getChunkNumber().length() == 4) {
			chunk.setChunkNumber("0" + chunk.getChunkNumber());
		}
		String chunkName = temPath + "/" + chunk.getIdentifier() + "_" + chunk.getChunkNumber();

		// 获取写入流
		InputStream is = file.getInputStream();
		this.fileService.upload(is, chunkName);
		// 上传成功则返回需要合并
		Map map = new HashMap();
		map.put("needMerge", true);
		return map;
	}

	/**
	 * 检查分片是否已经上传
	 *
	 * @param chunk
	 * @return
	 * @throws ProjectException
	 */
	@GetMapping("chunkupload")
	public Map checkChunk(Chunk chunk, Long userId) throws Exception {
		HashMap<String, Object> result = new HashMap<>();
		//User userInfo = userService.getUserById(userId);
		//获取当前上传块的md5值
		String identifier = chunk.getIdentifier();
		UserFile fileByMd5 = userFileService.getUserFileDao().selectOne(new QueryWrapper<UserFile>().lambda()
				.eq(UserFile::getMd5, identifier)
				.last("limit 1"));
		//如果根据md5找到相同的文件，并且大小一致
		if (fileByMd5 != null && fileByMd5.getFileSize().equals(Double.parseDouble(chunk.getTotalSize().toString()))) {
			result.put("skipUpload", true);
			result.put("needMerge", false);
			LocalDateTime date = LocalDateTime.now();
			fileByMd5.setId(null);
			int index = chunk.getFilename().lastIndexOf(".");
			String fileType = null;
			if (index != -1) {
				fileType = chunk.getFilename().substring(index + 1);
				String filename = chunk.getFilename().substring(0, index);
				UserFile userFile = this.userFileService.getUserFileDao().selectOne(new QueryWrapper<UserFile>().lambda()
						.eq(UserFile::getDirectoryId, chunk.getDirId())
						.eq(UserFile::getFileStatus, ActionType.OK.code)
						.eq(UserFile::getMd5, identifier));
				// 如果文件存在就把文件名加上日期在存储一遍
				if (userFile != null && StrUtil.equals(filename, userFile.getFileName())) {
					filename = filename + DateUtil.format(date, "yyyy-MM-dd_HH_mm");
				}
				chunk.setFilename(filename);
			}
			fileByMd5.setFileName(chunk.getFilename())
					.setFileType(fileType)
					.setDirectoryId(chunk.getDirId())
					.setFileStatus(ActionType.OK.code)
					.setCreateTime(date)
					.setUserId(userId)
					.setModifyTime(date);
			this.userFileService.save(fileByMd5);
			userService.updateStorage(userId, fileByMd5.getFileSize(), ActionType.OK.code);
			return result;
		}
		/**
		 *  如果不存在，则查询文件是否有上传
		 *  查询文件是否有上传过
		 *  获取文件路径，查询文件是否存在
		 */
		String tempPath = TEMP_PATH + "/" + chunk.getIdentifier();
		if (!this.fileService.exitFile(tempPath)) {
			return result;
		} else {
			List<Integer> ls = this.fileService.ls(tempPath);
			result.put("uploaded", ls);
			result.put("needMerge", true);
		}
		return result;
	}

	@GetMapping("md5")
	public ResponseEntity checkMD5(String md5, double fileSize) {
		if (StrUtil.isEmpty(md5)) {
			log.info("md5为空,查找错误");
		}
		UserFile userFileByMd5 = userFileService.getUserFileDao().selectOne(new QueryWrapper<UserFile>().lambda()
				.eq(UserFile::getMd5, md5));
		if (userFileByMd5 != null && userFileByMd5.getFileSize() == fileSize) {
			log.info("文件存在，发动秒传");
		}
		log.info("文件不存在可以上传");
		return ResponseEntity.ok("检验完毕");
	}

	@PostMapping("mergeFile")
	@Transactional
	public String mergeFile(@JsonParam(value = "md5", type = String.class) String md5,
							@JsonParam(value = "fileName", type = String.class) String fileName,
							@JsonParam(value = "totalSize", type = Integer.class) Integer totalSize,
							@JsonParam(value = "userId", type = Long.class) Long userId,
							@JsonParam(value = "dirId", type = Long.class) Long dirId) throws Exception {
		// 获取临时文件的路径
		FileStatus[] fileStatuses = this.fileService.tempFile(TEMP_PATH + "/" + md5);
		User userInfo = userService.getUserById(userId);
		LocalDateTime date = LocalDateTime.now();
		if (userInfo == null) {
			throw new ProjectException("合并失败,找不到此用户");
		}
		// 合并文件
		String dirPath = "/" + userInfo.getUsername() + "/" + DateUtil.format(date, "yyyy_MM_dd_HH_mm");
		this.fileService.mkdir(dirPath);
		String filePath = dirPath + "/" + fileName;
		this.fileService.mergeFile(fileStatuses, filePath);
		// 检查MD5是否一致
		String chechMD5 = this.fileService.chechMD5(filePath);
		if (StrUtil.equals(chechMD5, md5)) {
			UserFile userFile = new UserFile();
			int index = fileName.lastIndexOf(".");
			String type = null;
			if (index != -1) {
				type = fileName.substring(index + 1);
				fileName = fileName.substring(0, index);
			}
			userFile.setFileName(fileName);
			userFile.setFilePath(filePath);
			userFile.setDirectoryId(dirId);
			userFile.setCreateTime(date);
			userFile.setFileSize(Double.valueOf(totalSize));
			userFile.setMd5(md5);
			userFile.setFileStatus(ActionType.OK.code);
			userFile.setFileType(type);
			userFile.setUserId(userId);
			userFile.setModifyTime(date);
			// 保存文件
			this.userFileService.getUserFileDao().insert(userFile);
			// 更新用户信息
			userInfo.setUsedStorageSize(userFile.getFileSize() + userInfo.getUsedStorageSize());
			this.userService.updateUserInfoById(userInfo);
			// 合并完成删除文件
			this.fileService.rm(TEMP_PATH + "/" + md5);
			return "上传成功,文件合并成功";
		}
		return "文件合并失败";
	}

	/**
	 * 单文件下载
	 *
	 * @param response
	 * @return
	 * @throws ProjectException
	 */
	@GetMapping("download/{id}")
	public ResultVO fileDownload(HttpServletResponse response, @PathVariable("id") Long id) throws ProjectException {
		if (id == null || id <= 0) {
			throw new ProjectException("下载失败");
		}
		UserFile downloadFile = this.userFileService.getUserFileDao().selectById(id);
		String fileName;
		if (StrUtil.isNotEmpty(downloadFile.getFileType())) {
			fileName = downloadFile.getFileName() + "." + downloadFile.getFileType();
		} else {
			fileName = downloadFile.getFileName();
		}
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
			this.fileService.download(downloadFile.getFilePath(), response.getOutputStream());
		} catch (Exception e) {
			log.error("发生了不可描述的错误:", e);
		}
		return null;
	}

	@PostMapping("multDownload")
	public ResultVO multipleDownload(@JsonParam(value = "item", type = Item.class, required = false) List<Item> items, HttpServletResponse response) throws ProjectException, IOException, URISyntaxException, InterruptedException {
		if (items == null || items.isEmpty()) {
			throw new ProjectException("下载失败");
		}
		List<Directory> directories = new ArrayList<>();
		List<UserFile> userFiles = new ArrayList<>();
		for (Item item : items) {
			if (StrUtil.equals(item.getType(), "dir")) {
				Directory dir = this.directorySerivce.getDirById(item.getId());
				directories.add(dir);
			} else {
				UserFile userFile = this.userFileService.getUserFileDao().selectById(item.getId());
				userFiles.add(userFile);
			}
		}
		OutputStream outputStream = response.getOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		this.fileService.multipleDownload(directories, userFiles, zipOutputStream, "");
		zipOutputStream.close();
		return null;
	}
}
