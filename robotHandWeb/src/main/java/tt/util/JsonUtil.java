package tt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	public static Object keyJsonValue(String json, String key) {
		JSONObject jsonObject = JSON.parseObject(json);
		return jsonObject.get(key);
	}

	public static void main(String[] args) {

	}

	public static String mapToJson(Map map) {
		return JSONObject.toJSONString(map);
	}

	/**
	 * 
	 * @describe 将json集合转换成java对应对象集合
	 * @auto duanzaoxing
	 * @date
	 * @param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> jsonToJavaObject(JSONArray jsonArray, Class cs)
			throws InstantiationException, IllegalAccessException {
		List<T> list = new ArrayList<T>();
		for (Object jsonObject : jsonArray) {
			list.add((T) JSON.toJavaObject((JSONObject) jsonObject, cs));
		}
		return list;
	}

	/**
	 * 
	 * @describe 将json对象转换成java对应对象
	 * @auto duanzaoxing
	 * @date
	 * @param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T jsonToJavaObject(JSONObject jsonObject, Class cs) {
		return (T) JSONObject.toJavaObject(jsonObject, cs);
	}

	/**
	 * 格式化显示数据信息,方便日志显示
	 * 
	 * @Description:
	 * @author wsd
	 * @date 2014年7月28日 上午9:57:10
	 * @param obj
	 * @return
	 */
	public static String fmtObj2JsonStr(Object obj) {
		String res = "";
		try {
			res = JSON.toJSONString(obj);// 扩张
		} catch (Exception e) {
		}
		return res;
	}

	/**
	 * JSON转list
	 * 
	 * @author：rex
	 * @param json
	 * @return
	 */
	public static <T> List<T> json2List(final String json) {
		if (json != null) {
			return JSON.parseObject(json, List.class);
		}
		return null;
	}

}
