package org.lhq.controller;



import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.lhq.common.Item;
import org.lhq.common.Result;
import org.lhq.entity.Directory;
import org.lhq.exception.ProjectException;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.UserFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.PrivilegedActionException;
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
@Api(tags = "目录接口")
public class DirectoryController {
    @Resource
    DirectorySerivce directorySerivce;
    @Resource
    UserFileService userFileService;

	@ApiImplicitParam(name = "dirName",value = "文件夹名称",required = true)
	@ApiOperation(value = "新建目录")
    @PostMapping(value = "mkdir" )
    public Result mkdir(@RequestParam(value = "dirName",required = false) String dirName,
						@RequestParam(value = "pid",required = false) Long parentId,
						@RequestParam(value = "userId",required = false) Long userId) throws ProjectException {
        if (StrUtil.isEmpty(dirName)){
            throw new ProjectException("文件夹名为空");
        }
		// 如果父目录等于空，
        if (parentId == null){
			Directory directory = directorySerivce.getDirByPid(0L, userId);
			parentId = directory.getId();
		}

        Directory newDir = new Directory();
        newDir.setDirectoryName(dirName);
        newDir.setParentId(parentId);
        newDir.setUserId(userId);
        newDir.setCreateTime(new Date());
        newDir.setModifyTime(new Date());
        directorySerivce.saveDir(newDir);
        return new Result("新建成功").setMessage("新建成功");
    }
    @PostMapping("rename")
    public String updateDirName(String name,Long id) throws ProjectException {
        if (StrUtil.isEmpty(name) || id <= 0){
            throw new ProjectException("文件名为空");
        }
        Directory directory = directorySerivce.getDirById(id);
        if (directory == null){
        	throw  new ProjectException("文件夹不存在");
        }
        directory.setDirectoryName(name);
        directory.setModifyTime(new Date());
        directorySerivce.updateById(directory);
        return "目录重命名成功";
    }

	/**
	 * 获取该文件夹下的文件和文件夹
	 * @param pid
	 * @param userId
	 * @return
	 */
    @GetMapping("/getDir")
    public Map getDir(@RequestParam("pid") Long pid,@RequestParam("userId") Long userId) throws ProjectException {
    	//如果传上来的pid为空泽获取根目录
    	if (pid ==null || pid <= 0){
			Directory dir = directorySerivce.getDirByPid(0L, userId);
			if (dir!=null){
				pid = dir.getId();
			}
		}
		Directory directory = directorySerivce.getDirById(pid);
    	if (directory == null){
    		throw new ProjectException("目录不存在");
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
		result.put("path",ListUtil.reverse(parentDirs));
		return result;
	}

	/**
	 * 获取目录和子目录
	 * @param userId
	 * @return
	 */
	@GetMapping("listDir")
	public List getListDir(Long userId){
		Directory directory = this.directorySerivce.getDirByPid(0L, userId);
		List list = new ArrayList<>();
		list = directorySerivce.ListDir(directory.getId(), list);
		Map<String, Object> result = new HashMap<>();
		result.put("id",directory.getId());
		result.put("name","全部文件");
		result.put("children",list);
		List<Object> allList = new ArrayList<>();
		allList.add(result);
		return allList;
	}

	/**
	 * 删除文件
	 * @param list
	 * @return
	 */
	@PostMapping("deleteDir")
	public String deleteDir(@RequestBody List<Item> list){
		Boolean result = directorySerivce.deleteDirAndFile(list);
		return "删除"+ (result?"成功":"失败");
	}
	@PostMapping("copy")
	public ResponseEntity copy(@RequestBody List<Item> list,Long targetId){
    	directorySerivce.copyDirAndFile(list,targetId);
    	return null;
	}
	@PostMapping("move")
	public String move(Long sourceId,Long targetId){
		Boolean moveDir = directorySerivce.moveDir(sourceId, targetId);
		return "移动"+ (moveDir?"成功":"失败");
	}
	@GetMapping("/{name}")
	public ResponseEntity findByName(@PathVariable String name){
    	if (StrUtil.isEmpty(name)){
    		log.error("名字为空,查找失败");
		}
    	return new ResponseEntity("应阴阳",HttpStatus.OK);
	}


}
