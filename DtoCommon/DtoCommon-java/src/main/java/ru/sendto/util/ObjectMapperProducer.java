package ru.sendto.util;

import javax.enterprise.inject.Produces;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans.
 */
public class ObjectMapperProducer {

	static ObjectMapper om;

	@Produces
	public ObjectMapper getMapper() {
		if (om == null) {
			initOm();
		}
		return om;
	}

	synchronized private static void initOm() {
		if(om == null){
			om = new ObjectMapper();
			om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
			om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			
			om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
			om.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
			om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			om.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
			om.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
			om.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
			om.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, false);
			om.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
			om.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);

			om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		}
	}
	
}
