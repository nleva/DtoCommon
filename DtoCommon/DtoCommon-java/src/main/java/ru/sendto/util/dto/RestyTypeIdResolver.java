package ru.sendto.util.dto;

import java.util.Map;

import org.fusesource.restygwt.rebind.RestyJsonTypeIdResolver;

import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;

public class RestyTypeIdResolver implements RestyJsonTypeIdResolver {

	

	@Override
	public Map<String, Class<?>> getIdClassMap() {
		return Resolver.fillMap();
	}

	@Override
	public Class<? extends TypeIdResolver> getTypeIdResolverClass() {
		return Resolver.class;
	}

}
