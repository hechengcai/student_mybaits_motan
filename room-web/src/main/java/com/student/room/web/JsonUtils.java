package com.student.room.web;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static final ObjectMapper OM =new ObjectMapper();
	
	public static String toJson(Object obj) throws IOException {
		return OM.writeValueAsString(obj);
	}
	
	public static <T> T fromJson(Class<?> clazz, String json) throws IOException {
		return OM.readerFor(clazz).readValue(json);
	}
}
