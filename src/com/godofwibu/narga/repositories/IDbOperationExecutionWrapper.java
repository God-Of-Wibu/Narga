package com.godofwibu.narga.repositories;

public interface IDbOperationExecutionWrapper {
	<T> T execute(IOperation<T> operation) throws DataAccessLayerException;
	void execute(IOperationWithOutResult operation) throws DataAccessLayerException;
}
