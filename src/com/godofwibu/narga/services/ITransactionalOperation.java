package com.godofwibu.narga.services;

@FunctionalInterface
public interface ITransactionalOperation<T> {
	T doOperation() throws Exception;
}
