package org.lhq.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.lhq.gp.product.entity.Directory;


/**
 * @author hades
 */

public interface DirectoryDao extends BaseMapper<Directory> {
	@Select("select * from directory where id = #{id} and user_id = #{userId}")
    Directory getListParDirById(Long id,Long userId);

}
