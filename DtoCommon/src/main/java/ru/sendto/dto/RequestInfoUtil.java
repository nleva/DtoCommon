package ru.sendto.dto;

public class RequestInfoUtil {
	private RequestInfoUtil() {}
	public static void setRequestSample(RequestInfo dto, Class<Dto> sample) {
		try {
			dto.setR(sample.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public static Class<? extends Dto> getRequestClass(RequestInfo dto){
		return dto.getR().getClass();
	}
}
