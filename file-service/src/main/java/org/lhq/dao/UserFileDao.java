package org.lhq.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.lhq.entity.UserFile;

import java.util.List;

public interface UserFileDao extends BaseMapper<UserFile> {
	@Select("select * from user_file where directory_id = #{pid}")
	UserFile getFileByPid(Long pid);
	@Select("select * from user_file where directory_id = #{pid} and #{userId}")
	UserFile getFileByPid(Long pid, Long userId);
	@Select("select * from user_file where directory_id = #{pid}")
	List<UserFile> getListUserFileByPid(Long pid);
	@Select("select * from user_file where directory_id = #{pid} and #{userId}")
	List<UserFile> getListUserFileByPid(Long pid,Long userId);
	@Select("select * from user_file where md5 = #{md5}")
	UserFile getUserFileByMd5(String md5);
}
