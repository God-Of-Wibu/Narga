package com.godofwibu.narga.servlets.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface IHandler {
	String execute(HttpServletRequest req) throws IOException, ServletException;
}
