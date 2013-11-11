package com.gmail.dengtao.joe.commons.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JSONUtils {
	
	public static String pretty(String json) {
		try {
			if (StringUtils.isBlank(json)) return json;
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(json);
			return gson.toJson(je);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String compact(String json) {
		try {
			if (StringUtils.isBlank(json)) return json;
			Gson gson = new GsonBuilder().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(json);
			return gson.toJson(je);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}