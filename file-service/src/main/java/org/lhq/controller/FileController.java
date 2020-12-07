package org.lhq.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.lhq.gp.product.common.Chunk;
import org.lhq.gp.product.common.ResultCode;
import org.lhq.gp.product.entity.UserFile;
import org.lhq.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hades
 */
@RestController
@RequestMapping("tran")
@Slf4j
public class FileController {
	@Autowired
	FileService fileService;

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
	@PostMapping("upload")
	public void fileUpload(HttpServletResponse response, HttpServletRequest request, Chunk chunk){

	}
	@PostMapping("download")
	public void fileDownload(){}
}
