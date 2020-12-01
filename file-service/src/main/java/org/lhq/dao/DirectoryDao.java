package org.lhq.dao;


import org.lhq.gp.product.entity.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hades
 */
@Repository
public abstract class DirectoryDao implements JpaRepository<Directory,Long> {
	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
	@Override
	public Optional<Directory> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public <S extends Directory> S saveAndFlush(S s) {
		return null;
	}
}
