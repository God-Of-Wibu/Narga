package com.godofwibu.narga.repositories;

import java.util.List;

public interface ICurdRepository<TEntity, TId> {
	List<TEntity> findAll() ;
	TEntity findById(TId id) ;
	TId insert(TEntity entity) ;
	void update(TEntity entity) ;
	void deleteById(TId id) ;
	void delete(TEntity entity) ;
}
