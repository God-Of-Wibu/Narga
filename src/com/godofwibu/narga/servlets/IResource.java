package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.io.InputStream;

public interface IResource {
	String getName();
	long getContentLenght();
	InputStream getInputStream() throws IOException;
}
