package org.lhq.controller;



import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.lhq.gp.product.common.Item;
import org.lhq.gp.product.entity.Directory;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.UserFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.naming.Name;
import java.util.*;

/**
*@program: wangdefa_graduation_project
*@description: 目录Controller
*@author: Wang defa
*@create: 2020-12-04 09:51
*/

@RestController
@RequestMapping("dir")
@Slf4j
@Api(tags = "目录模块")
public class DirectoryController {
    @Resource
    DirectorySerivce directorySerivce;
    @Resource
    UserFileService userFileService;

	@ApiImplicitParam(name = "dirName",value = "文件夹名称",required = true)
	@ApiOperation(value = "新建目录")
    @PostMapping("mkdir")
    public ResponseEntity<Object> mkdir(String dirName, Long parentId, Long userId){
        if (StrUtil.isEmpty(dirName)){
            return new ResponseEntity<Object>("文件夹名称为空",HttpStatus.FORBIDDEN);
        }
        Directory dir = directorySerivce.getDirById(parentId);
		Directory directory = directorySerivce.getDirByPid(0L, userId);
		if (dir == null && directory != null){
			parentId = directory.getId();
		}

        Directory newDir = new Directory();
        newDir.setDirectoryName(dirName);
        newDir.setParentId(parentId);
        newDir.setUserId(userId);
        newDir.setCreateTime(new Date());
        newDir.setModifyTime(new Date());
        directorySerivce.saveDir(newDir);
        return new ResponseEntity<Object>("创建成功",HttpStatus.OK);
    }
    @PostMapping("rename")
    public ResponseEntity<Object> updateDirName(String dirName,Long id){
        if (StrUtil.isEmpty(dirName) || id <= 0){
            return new ResponseEntity<Object>("目录重命名失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Directory directory = directorySerivce.getDirById(id);
        if (directory == null){
            return new ResponseEntity<>("文件夹不存在",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        directory.setDirectoryName(dirName);
        directory.setModifyTime(new Date());
        directorySerivce.updateById(directory);
        return new ResponseEntity<Object>("目录重命名成功",HttpStatus.OK);
    }

	/**
	 * 获取该文件夹下的文件和文件夹
	 * @param pid
	 * @param userId
	 * @return
	 */
    @GetMapping("/getDir")
    public ResponseEntity<Object> getDir(@RequestParam("pid") Long pid,@RequestParam("userId") Long userId){
    	//如果传上来的pid为空泽获取根目录
    	if (pid ==null || pid <= 0){
			Directory dir = directorySerivce.getDirByPid(0L, userId);
			if (dir!=null){
				pid = dir.getId();
			}
		}
		Directory directory = directorySerivce.getDirById(pid);
    	if (directory == null){
    		return new ResponseEntity<>("目录不存在",HttpStatus.OK);
		}
		HashMap<String, Object> result = new HashMap<>(16);
		//获取目录
		List<Object> directories = directorySerivce.getListDircByPid(pid, userId);
		List<Object> userFiles = userFileService.getListFileByPid(pid, userId);
		List<Object> parentDirs = new ArrayList<>();
		parentDirs = directorySerivce.getListPartDirectoryById(pid, userId, parentDirs);
		result.put("id",pid);
		result.put("dirs",directories);
		result.put("file",userFiles);
		result.put("path",parentDirs);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	//删除一个目录和他下面的文件
	@GetMapping("deleteDir")
	public ResponseEntity<Object> deleteDir(@RequestBody List<Item> list){
		Boolean result = directorySerivce.deleteDirAndFile(list);

		return null;
	}
	@PostMapping("copy")
	public ResponseEntity copy(@RequestBody List<Item> list,Long targetId){
    	directorySerivce.copyDirAndFile(list,targetId);
    	return null;
	}
	public ResponseEntity move(Long sourceId,Long targetId){
		Boolean moveDir = directorySerivce.moveDir(sourceId, targetId);
		return null;
	}
	@GetMapping("/{name}")
	public ResponseEntity findByName(@PathVariable String name){
    	if (StrUtil.isEmpty(name)){
    		log.error("名字为空,查找失败");
		}
    	return new ResponseEntity("应阴阳",HttpStatus.OK);
	}


}
