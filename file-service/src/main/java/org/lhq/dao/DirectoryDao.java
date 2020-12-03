package org.lhq.dao;


import org.hibernate.Criteria;
import org.lhq.gp.product.entity.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hades
 */
@Repository
public abstract class DirectoryDao implements JpaRepository<Directory,Long> {

	@Query("select Directory from Directory where id = :id and userId = :userId")
	public abstract Directory getListParDirById(Long id, Long userId);
}
