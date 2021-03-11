package org.spring.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

// 클라이언트에서 넘어온 파일이 어떤 타입인지 확인 하는 클래스로 이미지 파일만 받을 것이기 때문에 이미지 파일값만 저장해서 return 해줌
public class MediaUtils {
	
	private static Map<String, MediaType> mediaMap;
	
	static {
		mediaMap = new HashMap<String, MediaType>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());
	}

}
