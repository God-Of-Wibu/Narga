package com.godofwibu.narga.utils;

@FunctionalInterface
public interface IOperation<T> {
	T execute() throws RuntimeException;
}
