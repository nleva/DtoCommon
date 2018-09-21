package ru.sendto.util.dto;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import lombok.extern.java.Log;

public class Resolver implements TypeIdResolver {
	
	static Logger log = Logger.getGlobal();
	
	@Override
	public String getDescForKnownTypeIds() {
		return null;
	}

	@Override
	public Id getMechanism() {
		return Id.CUSTOM;
	}

	@Override
	public String idFromBaseType() {
		return idFromValueAndType(null, base.getRawClass());
	}

	@Override
	public String idFromValue(Object arg0) {
		return idFromValueAndType(arg0, arg0.getClass());
	}

	@Override
	public String idFromValueAndType(Object arg0, Class<?> arg1) {
		return map.inverse().get(arg1);
	}

	JavaType						base;
	static BiMap<String, Class<?>>	map	= HashBiMap.create(100);

	@Override
	public void init(JavaType base) {
		this.base = base;
		fillMap();

	}

	static public BiMap<String, Class<?>> setMap(BiMap<String, Class<?>> map) {
		return Resolver.map=map;
	}

	static public BiMap<String, Class<?>> setMapByJson(String jsonMap) {
        try {
        	ObjectMapper om = new ObjectMapper();
			HashMap<String,String> simpleMap = om.readValue(jsonMap, HashMap.class);
			for (Entry<String, String> entry : simpleMap.entrySet()) {
				map.put(entry.getKey(), Class.forName(entry.getValue()));
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
			return null;
		}
        
		return Resolver.map;
	}

	static public BiMap<String, Class<?>> setMapByResource(InputStream jsonResource) {
        try(InputStream resolverMap = jsonResource) {
        	ObjectMapper om = new ObjectMapper();
        	 HashMap<String,String> simpleMap = om.readValue(resolverMap, HashMap.class);
             for (Entry<String,String> e : simpleMap.entrySet()){
                 map.put(e.getKey(),Class.forName(e.getValue()));
             }
		} catch (Exception e) {
			log.severe(e.getMessage());
			return null;
		}
        
		return Resolver.map;
	}
	
	static public BiMap<String, Class<?>> fillMap() {
		return map;
	}


	@Override
	public JavaType typeFromId(DatabindContext ctx, String id) throws IOException {
		return TypeFactory.defaultInstance().constructSpecializedType(base, map.get(id));
	}

}
