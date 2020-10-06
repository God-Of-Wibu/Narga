package com.godofwibu.narga.repositories;

import java.util.List;

public interface ICurdRepository<TEntity, TId> {
	List<TEntity> getAll();
	TEntity get(TId id);
	void insert(TEntity entity);
	void update(TEntity entity);
	void delete(TId id);
}
