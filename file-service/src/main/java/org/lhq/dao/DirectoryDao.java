package org.lhq.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.lhq.gp.product.entity.Directory;

import java.util.List;


/**
 * @author hades
 */

public interface DirectoryDao extends BaseMapper<Directory> {
	@Select("select * from directory where id = #{id} and user_id = #{userId}")
    Directory getListParDirById(Long id,Long userId);
	@Select("select * from directory where parent_id = #{pid}")
	Directory getDirByPid(Long pid);
	@Select("select * from directory where parent_id = #{pid} and user_id = #{userId}")
	Directory getDirByPid(Long pid,Long userId);
	@Select("select * from directory where parent_id = #{pid}")
	List<Directory> getListDirByPid(Long pid);
	@Select("select * from directory where parent_id = #{pid} and user_id = #{userId}")
	List<Directory> getListDirByPid(Long pid,Long userId);

}
