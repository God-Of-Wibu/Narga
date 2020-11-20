package com.godofwibu.narga.utils;

@FunctionalInterface
public interface IOperationWithOutResult {
	void execute() throws RuntimeException;
}
