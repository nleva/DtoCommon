package ru.sendto.util.dto;

import javax.inject.Inject;
import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonConfig implements ContextResolver<ObjectMapper> {

	@Inject
	ObjectMapper objectMapper;

	@Override
	public ObjectMapper getContext(Class<?> arg0) {
		return objectMapper;
	}
	
}