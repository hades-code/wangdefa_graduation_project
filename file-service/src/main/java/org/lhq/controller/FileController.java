package org.lhq.controller;

import lombok.extern.slf4j.Slf4j;
import org.lhq.gp.product.common.Chunk;
import org.lhq.gp.product.entity.UserFile;
import org.lhq.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hades
 */
@RestController
@RequestMapping("tran")
@Slf4j
public class FileController {
	@Autowired
	FileService fileService;

	@PostMapping("upload")
	public void fileUpload(HttpServletResponse response, HttpServletRequest request, Chunk chunk){

	}
	@PostMapping("download")
	public void fileDownload(){}
}
