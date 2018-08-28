package ru.sendto.util.dto;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reflections.Reflections;

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

	static public BiMap<String, Class<?>> fillMap() {
		if (!map.isEmpty())
			return map;
		Set<Class<? extends Dto>> sub = Collections.newSetFromMap(new ConcurrentHashMap<>());
		
		
		Stream<String> pkgs = Arrays.stream(System.getenv("ru.sendto.util.dto.packages").split(","));
		Stream<String> defaultPkgs = Arrays.stream(new String[] {"ru.sendto.dto","dto"});
		
		Stream.concat(pkgs,defaultPkgs)
			.peek(String::trim)
			.filter(s->!s.isEmpty())
			.distinct().map(Reflections::new)
			.flatMap(r->r.getSubTypesOf(Dto.class).stream())
			.forEach(clz -> {
			
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

		});
		System.out.println(map);
		return map;
	}

	@Override
	public JavaType typeFromId(DatabindContext ctx, String id) throws IOException {
		return TypeFactory.defaultInstance().constructSpecializedType(base, map.get(id));
	}

//	public static void main(String[] args) throws JsonProcessingException {
//		Resolver r = new Resolver();
//		ObjectMapper mapper = new ObjectMapper();
//		MessageTextDto dto = new MessageTextDto().setText("ok");
//		String json = mapper.writeValueAsString(dto);
//		System.out.println(json);
//		System.out.println(r.idFromValue(dto));
//	}
}
