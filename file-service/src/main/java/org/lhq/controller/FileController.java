package org.lhq.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hades
 */
@RestController
@RequestMapping("file")
public class FileController {
	@PostMapping("upload")
	public void fileUpload(){}
	@PostMapping("download")
	public void fileDownload(){}
}
