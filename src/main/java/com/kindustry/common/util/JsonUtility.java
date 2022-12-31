package com.kindustry.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.type.CollectionType;

/**
 * JSON转换工具类
 * 
 */
public class JsonUtility {

	/**
	 * read json file by path
	 * 
	 * @param jsonPath
	 * @return
	 */
	public static String readJsonFile(String jsonPath) {
		return readJsonFile(jsonPath, null);
	}

	/**
	 * 获得 json 字符串 read json file by path
	 * 
	 * @param jsonPath
	 * @return
	 */
	public static String readJsonFile(String jsonPath, String nodeName) {
		String strResult = "";
		ObjectMapper objMapper = new ObjectMapper();

		try {
			JsonNode rootNode = objMapper.readTree(ClassLoader.getSystemResourceAsStream(jsonPath));

			if (null != nodeName) {
				// 指定某一个节点
				strResult = rootNode.get(nodeName).toString();
			} else {
				strResult = rootNode.toString();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strResult;
	}
	

	/**
	 * @param jsonPath
	 * @param nodeName
	 * @return
	 */
	public static List<String> readJsonArray(String jsonPath, String nodeName) {
		List<String> result =  new ArrayList<String>();
		ObjectMapper objMapper = new ObjectMapper();
		try {
			JsonNode rootNode = objMapper.readTree(ClassLoader.getSystemResourceAsStream(jsonPath));
			if (null != nodeName) {
				// 指定某一个节点
				rootNode = rootNode.get(nodeName);
				JsonNodeType jsonNodeType = rootNode.getNodeType() ;
				switch (jsonNodeType) {
				case ARRAY:
					Iterator<JsonNode> it =	rootNode.elements();
					while(it.hasNext()) {
					  JsonNode item =	it.next();
					  result.add(item.toString());
					}
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	

	/**
	 * 获取指定节点
	 * @param jsonText
	 * @param nodeName
	 * @return
	 */
	public static String getJson(String jsonText, String nodeName) {
		ObjectMapper objMapper = new ObjectMapper();
		try {
			JsonNode rootNode = objMapper.readTree(jsonText);
			if (null != nodeName) {
				rootNode = rootNode.get(nodeName);
				return rootNode.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	

	/**
	 * JSON字符串转换成对象
	 * 
	 * @param jsonString 需要转换的字符串
	 * @param type       需要转换的对象类型
	 * @return 对象
	 */
	public static <T> T fromJson(String jsonString, Class<T> type) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(jsonString, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	

	/**
	 * 将JSONArray对象转换成list集合
	 * 
	 * @param jsonArr
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonString, Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
		try {
			return mapper.readValue(jsonString, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 将JSONArray对象转换成数组
	 * @param <T>
	 * @param jsonString
	 * @param type
	 * @return
	 */
	public static <T> T[] jsonToArray(String jsonString, Class<T[]> type) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(jsonString, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 将json字符串转换成map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonToMap(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对象转换成JSON字符串
	 * 
	 * @param obj 需要转换的对象
	 * @return 对象的string字符
	 */
	public static String toJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 对象转换成JSON字符串
	 * 
	 * @param obj 需要转换的对象
	 * @return 对象的string字符
	 */
	public static String toJson(String key, Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, obj);
		return toJson(map);
	}

}