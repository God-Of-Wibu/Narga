package com.godofwibu.narga.services;

@FunctionalInterface
public interface ITransactionalOperationWithOutResult {
	void doOperation() throws Exception;
}
