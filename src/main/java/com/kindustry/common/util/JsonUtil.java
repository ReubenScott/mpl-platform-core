package com.kindustry.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON转换工具类
 * 
 */
public class JsonUtil {

  /**
   * 对象转换成JSON字符串
   * 
   * @param obj
   *          需要转换的对象
   * @return 对象的string字符
   */
  public static String toJson(Object obj) {
    ObjectMapper mapper = new ObjectMapper();
    String json = null;
    try {
      json = mapper.writeValueAsString(obj);
    } catch (JsonGenerationException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return json;
  }

  /**
   * 对象转换成JSON字符串
   * 
   * @param obj
   *          需要转换的对象
   * @return 对象的string字符
   */
  public static String toJson(String key, Object obj) {
    Map map = new HashMap();
    map.put(key, obj);
    return toJson(map);
  }

  /**
   * JSON字符串转换成对象
   * 
   * @param jsonString
   *          需要转换的字符串
   * @param type
   *          需要转换的对象类型
   * @return 对象
   */
  public static <T> T fromJson(String jsonString, Class<T> type) {
    // JSONObject jsonObject = JSONObject.fromObject(jsonString);
    // return (T) JSONObject.toBean(jsonObject, type);

    return null;
  }

  /**
   * 将JSONArray对象转换成list集合
   * 
   * @param jsonArr
   * @return
   */
  // public static List<Object> jsonToList(JSONArray jsonArr) {
  // List<Object> list = new ArrayList<Object>();
  // for (int i = 0; i < jsonArr.length(); i++) {
  // Object obj = jsonArr.get(i);
  // if (obj instanceof JSONArray) {
  // list.add(jsonToList((JSONArray) obj));
  // } else if (obj instanceof JSONObject) {
  // list.add(jsonToMap((JSONObject) obj));
  // } else {
  // list.add(obj);
  // }
  // }
  // return list;
  // }

  /**
   * 将json字符串转换成map对象
   * 
   * @param json
   * @return
   */
  public static Map<String, Object> jsonToMap(String json) {
    return null;
  }

  /**
   * 将JSONObject转换成map对象
   * 
   * @param json
   * @return
   */
  // public static Map<String, Object> jsonToMap(JSONObject obj) {
  // Set<String> set = obj.keySet();
  // Map<String, Object> map = new HashMap<String, Object>(set.size());
  // for (String key : set) {
  // Object value = obj.get(key);
  // if (value instanceof JSONArray) {
  // map.put(key.toString(), jsonToList((JSONArray) value));
  // } else if (value instanceof JSONObject) {
  // map.put(key.toString(), jsonToMap((JSONObject) value));
  // } else {
  // map.put(key.toString(), obj.get(key));
  // }
  //
  // }
  // return map;
  // }
}