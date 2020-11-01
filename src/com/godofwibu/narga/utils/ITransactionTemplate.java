package com.godofwibu.narga.utils;

import com.godofwibu.narga.repositories.DataAccessLayerException;

public interface ITransactionTemplate {
	<T> T execute(IOperation<T> operation) throws DataAccessLayerException;
	void execute(IOperationWithOutResult operation) throws DataAccessLayerException;
}
