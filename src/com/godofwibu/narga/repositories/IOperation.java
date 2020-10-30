package com.godofwibu.narga.repositories;

@FunctionalInterface
public interface IOperation<T> {
	T doStuff() throws RuntimeException;
}
