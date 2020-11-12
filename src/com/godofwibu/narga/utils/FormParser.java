package com.godofwibu.narga.utils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class FormParser {
	private Map<Class<?>, IConverter<?>> converters;

	public FormParser() {
		converters = new HashMap<Class<?>, IConverter<?>>();
	}
	
	public <T> T getFormObject(HttpServletRequest req, Class<T> type)
			throws NoSuchConverterException, IOException, ServletException, NoSuchParameterException {
		T formData;
		Map<String, String[]> parameterMap = req.getParameterMap();
		String parameterName = null;
		try {
			formData = newInstanceWithDefaultCtor(type);
			for (var field : type.getDeclaredFields()) {
				parameterName = getParameterName(field);
				field.setAccessible(true);
				if (field.getType().equals(Part.class)) {
					Part part = req.getPart(parameterName);
					if (part == null)
						throw new NoSuchParameterException("no such parameter " + parameterName,  parameterName);
					field.set(formData, part);
				} else {
					IConverter<?> converter = converters.get(field.getType());
					if (converter == null)
						throw new NoSuchConverterException(field.getType());
					String[] reqParam = parameterMap.get(parameterName);
					if (reqParam == null) {
						throw new NoSuchParameterException("no such parameter " + parameterName, parameterName);
					} else {
						field.set(formData, converter.convert(reqParam));
					}
				}
			}
			return formData;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException e) {
			throw new ServletException(e);
		}

	}

	@SuppressWarnings("unchecked")
	private <T> T newInstanceWithDefaultCtor(Class<T> type)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		Constructor<T> defaultConstructor = null;
		for (Constructor<?> constructor : type.getDeclaredConstructors()) {
			if (constructor.getParameterCount() == 0) {
				defaultConstructor = (Constructor<T>) constructor;
				break;
			}
		}
		if (defaultConstructor == null) {
			throw new NoSuchMethodException(type.getName() + " class type does not have default constructor");
		}
		defaultConstructor.setAccessible(true);
		return defaultConstructor.newInstance();
	}

	public FormParser addConverter(Class<?> type, IConverter<?> converter) {
		converters.put(type, converter);
		return this;
	}

	private String getParameterName(Field field) {
		ParameterName parameterNameAnotation = field.getAnnotation(ParameterName.class);
		return parameterNameAnotation != null ? parameterNameAnotation.value() : field.getName();
	}
}
