package com.rockbb.thor.commons.lib.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectUtil {

	private static Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

	public static Object createInstance(String className) {
		Object obj = null;
		try {
			obj = (Class.forName(className).getClassLoader().loadClass(className)).newInstance();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException", e);
		} catch (InstantiationException e) {
			logger.error("InstantiationException", e);
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException", e);
		} catch (Exception e) {
			logger.error("Unknown Exception", e);
		}
		return obj;
	}

	public static <T> Map<String, List<String>> compare(T pre, T post, Class<T> t) {
		Map<String, List<String>> diff = new HashMap<>();
		Field[] fields = t.getDeclaredFields();
		if (fields != null) {
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					if (pre == null) {
						Object property2 = field.get(post);
						diff.put(field.getName(), Arrays.asList("null", property2.toString()));
					} else if (post == null) {
						Object property1 = field.get(pre);
						diff.put(field.getName(), Arrays.asList(property1.toString(), "null"));
					} else {
						Object property1 = field.get(pre);
						Object property2 = field.get(post);
						if (property1 == null && property2 == null) {
							// Do nothing
						} else if (property1 == null) {
							diff.put(field.getName(), Arrays.asList("null", property2.toString()));
						} else if (property2 == null) {
							diff.put(field.getName(), Arrays.asList(property1.toString(), "null"));
						} else if (!property1.equals(property2)) {
							diff.put(field.getName(), Arrays.asList(property1.toString(), property2.toString()));
						}
					}
				} catch (IllegalAccessException e) {
					logger.error("IllegalAccessException ", e);
				} catch (Exception e) {
					logger.error("Unknown Exception ", e);
				} finally {
					field.setAccessible(false);
				}
			}
		}
		return diff;
	}

	/**
	 * 将对象转换为Map<String, String>的形式
	 *
	 * @param obj 对象
	 * @param skipEmpty 当值为null或空字符串时, 是否跳过不保留, true:跳过不保留, false:保留
	 * @param excludeFields 需要忽略的字段列表, null:无忽略
	 * @return HashMap
	 */
	public static Map<String, String> objToMap(Object obj, boolean skipEmpty, List<String> excludeFields) {
		Map<String, String> result = new HashMap<>();
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		while (!cls.getName().equals("java.lang.Object")) {
			for (Field field : fields) {
				if(excludeFields != null && excludeFields.contains(field.getName())) continue;
				try {
					field.setAccessible(true);
					Object property = field.get(obj);
					if (property == null) {
						if (skipEmpty) continue;
						else result.put(field.getName(), "null");
					} else {
						String value = property.toString();
						if (skipEmpty && value.length() == 0) continue;
						result.put(field.getName(), value);
					}
				} catch (IllegalAccessException e) {
					logger.error("IllegalAccessException", e);
				}
			}
			cls = cls.getSuperclass();
			fields = cls.getDeclaredFields();
		}
		return result;
	}


}
