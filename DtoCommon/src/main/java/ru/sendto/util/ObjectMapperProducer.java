package ru.sendto.util;

import javax.enterprise.inject.Produces;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans.
 */
public class ObjectMapperProducer {

	ObjectMapper objectMapper;

	@Produces
	public ObjectMapper getMapper() {
		if(objectMapper == null){
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}
	
}
