package namoo.club.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namoo.club.util.JsonListUtil.TeamMember;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class JsonListUtil<T> {
	//	
	public static String toJson(Object object) {
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(Date.class, getJsonDateSerializer())
			.registerTypeAdapter(Date.class, getJsonDateDeserializer())
			.create();
		return gson.toJson(object);
	}
	
	public static Object fromJson(String jsonString, Class<?> clazz) {
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(Date.class, getJsonDateSerializer())
			.registerTypeAdapter(Date.class, getJsonDateDeserializer())
			.create();
		
		return gson.fromJson(jsonString, clazz); 
	} 
	
	public Object fromJsonArray(String jsonString, Class<?> clazz) {
		//
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(Date.class, getJsonDateSerializer())
			.registerTypeAdapter(Date.class, getJsonDateDeserializer())
			.create();
		
		return gson.fromJson(jsonString, new TypeToken<List<T>>(){}.getType());
	}

	
	
	public class TeamMember {
		public String name; 
		public int age; 
		
		public TeamMember(String name, int age) {
			this.name = name; 
			this.age = age; 
		}
	}
	
	private static JsonSerializer<Date> getJsonDateSerializer() {
		//
		return new JsonSerializer<Date>() {
			//
			@Override
			public JsonElement serialize(Date src, Type typeOfSrc,
					JsonSerializationContext context) {
				// 
				return new JsonPrimitive(src.getTime());
			}
		};
	}
	
	private static JsonDeserializer<Date> getJsonDateDeserializer() {
		//
		return new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context)
					throws JsonParseException {
				// 
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		};
	}
}