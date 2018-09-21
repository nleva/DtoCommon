package ru.sendto.util.dto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.Reference;

import org.reflections.Reflections;
import org.reflections.util.FilterBuilder;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import ru.sendto.dto.Dto;

public class Resolver implements TypeIdResolver {

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

	static final String propKey = "DTO_LOOKUP_PACKAGES";
	
	static public BiMap<String, Class<?>> fillMap() {
		if (!map.isEmpty())
			return map;
		
		Stream<String> propsPkgs = Arrays.stream(Optional.ofNullable(System.getProperty(propKey)).orElse("").split(","));
		Stream<String> envPkgs = Arrays.stream(Optional.ofNullable(System.getenv(propKey)).orElse("").split(","));
		Stream<String> defaultPkgs = Arrays.stream(new String[] {"ru.sendto.dto","dto"});
		
		Stream.concat(Stream.concat(propsPkgs,defaultPkgs),envPkgs)
			.peek(String::trim)
			.filter(s->!s.isEmpty())
			.distinct().map(Reflections::new)
			.flatMap(r->r.getSubTypesOf(Dto.class).stream())
			.forEach(Resolver::putClassToMap);
		System.out.println(map);
		return map;
	}

	private static void putClassToMap(Class<? extends Dto> clz) {
		if(Modifier.isAbstract(clz.getModifiers()))
			return;
		
		String id;
		JsonTypeName typeName = clz.getAnnotation(JsonTypeName.class);
		if (typeName != null && !(id = typeName.value()).isEmpty() && !map.containsKey(id)) {
//			} else if (!map.containsKey(id = clz.getSimpleName().replaceAll("[^A-Z0-9]", ""))) {
//			} else if (!map.containsKey(id = clz.getSimpleName())) {
			// } else if (!map.containsKey(id = clz.getName())) {
		} else if (!map.containsKey(id = Base64.getEncoder().withoutPadding().encodeToString(ByteBuffer.allocate(4).putInt(clz.getCanonicalName().hashCode()).array()))) {
		} else if (!map.containsKey(id = clz.getCanonicalName())) {
		} else {
			throw new RuntimeException("Resolver cann`t create id for " +
					clz.getCanonicalName() + ". "
					+ "There are another classes with the same id. Try another @JsonTypeName.");
		}
		map.put(id, clz);
	}

	@Override
	public JavaType typeFromId(DatabindContext ctx, String id) throws IOException {
		return TypeFactory.defaultInstance().constructSpecializedType(base, map.get(id));
	}

}
