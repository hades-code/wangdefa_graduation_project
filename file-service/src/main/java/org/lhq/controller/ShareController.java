package org.lhq.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.lhq.common.Item;
import org.lhq.service.IShareService;
import org.lhq.service.IVirtualAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hades
 */
@RestController
@Slf4j
@Api(tags = "文件分享接口")
@RequestMapping("share")
public class ShareController {
	@Resource
	IShareService shareService;
	@Resource
	IVirtualAddressService virtualAddressService;
	@GetMapping("/{shareCode}")
	public void shareLink(@PathVariable String shareCode){

	}
	@PostMapping("shareFile")
	public ResponseEntity shareDirAndFile(List<Item> item, Long userId){
		Object file = shareService.shareDirAndFile(item, userId);
		return ResponseEntity.ok(file);
	}
}
