package org.lhq.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hades
 */
@RestController
@Slf4j
@Api(tags = "文件分享")
@RequestMapping("share")
public class ShareController {
	@GetMapping("/{shareCode}")
	public void shareLink(@PathVariable String shareCode){

	}
}
