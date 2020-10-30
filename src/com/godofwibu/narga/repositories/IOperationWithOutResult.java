package com.godofwibu.narga.repositories;

@FunctionalInterface
public interface IOperationWithOutResult {
	void doStuff() throws RuntimeException;
}
