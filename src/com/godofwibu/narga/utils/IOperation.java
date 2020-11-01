package com.godofwibu.narga.utils;

@FunctionalInterface
public interface IOperation<T> {
	T doStuff() throws RuntimeException;
}
