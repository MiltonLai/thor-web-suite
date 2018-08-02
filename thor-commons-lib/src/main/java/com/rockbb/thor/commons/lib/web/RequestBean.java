package com.rockbb.thor.commons.lib.web;


import com.rockbb.thor.commons.lib.json.JacksonUtils;
import com.rockbb.thor.commons.lib.net.InetAddressUtil;
import com.rockbb.thor.commons.lib.utilities.StaticConfig;
import com.rockbb.thor.commons.lib.utilities.TimeUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestBean
{
	public static final String ATTR_KEY = "REQUEST_BEAN";

	private HttpServletRequest request;
	private HttpServletResponse response;
	private long timestamp = 0L;
	private String ip;
	private String userAgent;
	private Map<String, Object> data = new HashMap<>();

	public RequestBean(
			HttpServletRequest request,
			HttpServletResponse response,
			long timestamp) {
		this.request = request;
		this.response = response;
		this.timestamp = timestamp;
		this.ip = InetAddressUtil.getAddressFromRequest(request);
		this.userAgent = request.getHeader("User-Agent");
	}

	public HttpServletRequest getRequest() { return request; }
	public HttpServletResponse getResponse() { return response; }
	public long getTimestamp() { return timestamp; }
	public String getIp() { return ip; }
	public String getUserAgent() { return userAgent; }

	public void destroy() {
		if (data != null) data.clear();
	}

	public String getPath() {
		return request.getContextPath() + request.getServletPath();
	}

	public Map<String, Object> getData() {return data;}
	public void addObject(String key, Object value){data.put(key, value);}
	public void removeObject(String key){data.remove(key);}

	public String jsonEncode(){
		return JacksonUtils.compressMap(data);
	}

	/**
	 * @return 形如 {"info":"something"} 格式的信息内容
	 */
	public String jsonInfo(String info) {
		data.put("info", info);
		return jsonEncode();
	}

	/**
	 * @return 形如 {"info":"something", "field":"something"} 格式的信息内容
	 */
	public String jsonInfo(String info, String field) {
		data.put("info", info);
		data.put("field", field);
		return jsonEncode();
	}

	/**
	 * @return 形如 {"success":"something","field":"url"} 格式的信息内容
	 */
	public String jsonSuccess(String message, String field) {
		data.put("success", message);
		data.put("field", field);
		return jsonEncode();
	}
	public String jsonSuccess(String message) {
		data.put("success", message);
		return jsonEncode();
	}

	public String getRedirect() {
		String redirect = request.getParameter("redirect");
		if (redirect == null || redirect.equals("")) {
			redirect = request.getHeader("Referer");
		}
		// If the redirect link is empty or a user panel page, set it to the home page
		if (redirect == null
				|| redirect.length() == 0
				|| redirect.matches(".*\\/(message|login|logout)\\.html")) {
			redirect = baseLink();
		}
		return redirect;
	}

	/**
	 * Get the site base link according to the request
	 * You can use configuration instead
	 *
	 * @return site base link (e.g. http://aaa.bbb.ccc:8080)
	 */
	public String getSiteBase() {
		return StaticConfig.get("site_base");
	}

	public static String baseLink() {
		return baseLink("/");
	}

	public static String baseLink(String path) {
		return StaticConfig.get("root_path") + path;
	}

	public static String baseLink(String path, List<NameValuePair> nvpairs) {
		StringBuilder url = new StringBuilder();
		url.append(StaticConfig.get("root_path"));

		if (path != null && path.length() > 0) {
			url.append(path);
		}

		if (nvpairs != null && nvpairs.size() > 0) {
			String paramsStr = URLEncodedUtils.format(nvpairs, Charset.forName(StaticConfig.get("encoding")));
			if (url.indexOf("?") == -1)
				url.append('?');
			else
				url.append('&');
			url.append(paramsStr);
		}
		return url.toString();
	}

	public Pager getPager() {
		return new Pager(getInt("offset"), getInt("limit", 20), getInt("sort"), getInt("order"));
	}

	/* HTTP request parameters handlers */

	public String getParameter(String key) {
		return request.getParameter(key);
	}

	public String getHeader(String key) {
		return request.getHeader(key);
	}

	/**
	 * 获取request中的字符串数组参数
	 *
	 * @param key 参数名
	 * @param default_array 值为空的情况下的默认数组
	 * @return 数组
	 */
	public String[] getArray(String key, String[] default_array) {
		String[] array = request.getParameterValues(key);
		if (array == null || array.length == 0) {
			array = default_array;
		}
		return array;
	}

	/**
	 * 获取request中的字符串数组参数
	 *
	 * @param key 参数名
	 * @param default_array 值为空的情况下的默认数组
	 * @param candidates 取值范围, 不在这个范围内的会被过滤掉, 不能为空
	 * @return 数组
	 */
	public String[] getArray(String key, String[] default_array,
							 String[] candidates) {
		String[] array = request.getParameterValues(key);
		if (array == null || array.length == 0) {
			array = default_array;
		}
		List<String> arrayList = Arrays.asList(array);
		Arrays.sort(candidates); // 首先对数组排序
		for (int i = 0; i < arrayList.size();) {
			if (Arrays.binarySearch(candidates, arrayList.get(i)) < 0)
				arrayList.remove(i);
			else
				i++;
		}
		return arrayList.toArray(new String[]{});
	}

	/**
	 * 获取request中的整数数组参数
	 *
	 * @param key 参数名
	 * @param default_array 值为空的情况下的默认数组
	 * @param candidates 取值范围, 不在这个候选集合内的会被过滤掉, 为空则不限制
	 * @return 数组
	 */
	public int[] getIntArray(String key, int[] default_array,
							 Integer[] candidates) {
		String[] array = request.getParameterValues(key);
		if (array == null || array.length == 0) { return default_array; }

		int[] ints = new int[array.length];
		int pos = 0;
		if (candidates != null) {
			Arrays.sort(candidates);
			for (int i = 0; i < array.length; i++) {
				int value = 0;
				try {
					value = Integer.parseInt(array[i]);
				} catch (NumberFormatException e) {
					continue;
				}

				if (Arrays.binarySearch(candidates, value) >= 0) {
					ints[pos] = value;
					pos++;
				}
			}
		} else {
			for (int i = 0; i < array.length; i++) {
				int value = 0;
				try {
					value = Integer.parseInt(array[i]);
				} catch (NumberFormatException e) {
					continue;
				}

				ints[pos] = value;
				pos++;
			}
		}

		return ArrayUtils.subarray(ints, 0, pos);
	}

	/**
	 * 获取request中的整数数组参数
	 *
	 * @param key 参数名
	 * @param default_array 值为空的情况下的默认数组
	 * @param min	取值范围的下限(含), minimum value, included
	 * @param max	取值范围的上限(含), maximum value, included
	 * @return 数组
	 */
	public int[] getIntArray(String key, int[] default_array, int min, int max) {
		String[] array = request.getParameterValues(key);
		if (array == null || array.length == 0) { return default_array; }
		int[] ints = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			int value = 0;
			try {
				value = Integer.parseInt(array[i]);
			} catch (NumberFormatException e) {
				continue;
			}

			if (value < min)
				ints[i] = min;
			else if (value > max)
				ints[i] = max;
			else
				ints[i] = value;
		}
		return ints;
	}

	/**
	 * 读取 2016-01-01 12:30:30 格式的时间
	 */
	public Date getDateYmdhms(String key) {
		return TimeUtil.getDate(get(key), TimeUtil.FORMAT_YMD_HMS);
	}

	/**
	 * 读取 2016-01-01 格式的日期
	 */
	public Date getDateYmd(String key) {
		return TimeUtil.getDate(get(key), TimeUtil.FORMAT_YMD);
	}

	/**
	 * 读取request中的BigDecimal参数, 值为空或出现异常情况下返回0
	 *
	 * @param key 参数名
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(String key) {
		return getBigDecimal(key, BigDecimal.ZERO, -1);
	}

	public BigDecimal getBigDecimal(String key, int scale) {
		return getBigDecimal(key, BigDecimal.ZERO, scale);
	}

	/**
	 * 读取request中的BigDecimal参数
	 *
	 * @param key 参数名
	 * @param default_value 值为空或出现异常情况下的默认值
	 * @param scale 精度限制, -1为不限制
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(String key, BigDecimal default_value, int scale) {
		BigDecimal value = default_value;
		String str_value = request.getParameter(key);
		if (str_value != null) {
			try {
				DecimalFormat df = new DecimalFormat();
				df.setParseBigDecimal(true);
				value = (BigDecimal)df.parse(str_value);
				if (scale > -1 && value.scale() > scale) {
					value = value.setScale(scale, BigDecimal.ROUND_HALF_UP);
				}
			} catch (Exception e) {
				return default_value;
			}
		}
		return value;
	}

	/**
	 * 获取request中的浮点数参数
	 *
	 * @param key 参数名
	 * @param default_value 为空时的默认值
	 * @return 浮点值
	 */
	public Float getFloat(String key, float default_value) {
		String str_value = request.getParameter(key);
		float value = default_value;
		if (str_value != null) {
			try {
				value = Float.parseFloat(str_value.trim());
			} catch (Exception e) {
				value = default_value;
			}
		}
		return value;
	}

	/**
	 * 获取request中的浮点数参数, 值必须在candidates中, 否则使用默认值
	 *
	 * @param key 参数名
	 * @param default_value 参数为空时的默认值
	 * @param candidates 取值范围, 不在这个候选集合内的会被过滤掉, 为空则不限制
	 * @return 浮点值
	 */
	public Float getFloat(String key, float default_value, float[] candidates) {
		String str_value = request.getParameter(key);
		float value = default_value;
		if (str_value != null) {
			try {
				value = Float.parseFloat(str_value.trim());
				Arrays.sort(candidates); // 棣栧厛瀵规暟缁勬帓搴�
				if (Arrays.binarySearch(candidates, value) < 0)
					value = default_value;
			} catch (Exception e) {
				value = default_value;
			}
		}
		return value;
	}

	/**
	 * 获取request中的浮点数参数, 值必须在min..max区间中, 否则使用默认值
	 *
	 * @param key 参数名
	 * @param default_value 参数为空的情况下的默认值
	 * @param min	取值范围的下限(含), minimum value, included
	 * @param max	取值范围的上限(含), maximum value, included
	 * @return 浮点值
	 */
	public Float getFloat(String key, float default_value, float min, float max) {
		String str_value = request.getParameter(key);
		float value = default_value;
		if (str_value != null) {
			try {
				value = Float.parseFloat(str_value.trim());
				if (value < min || value > max)
					value = default_value;
			} catch (Exception e) {
				value = default_value;
			}
		}
		return value;
	}

	/**
	 * 获取request中的整数参数, 默认为0
	 *
	 * @param key 参数名
	 * @return 整数
	 */
	public int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * 获取request中的整数参数
	 *
	 * @param key 参数名
	 * @param default_value 参数为空时的默认值
	 * @return 整数
	 */
	public int getInt(String key, int default_value) {
		String str_value = request.getParameter(key);
		int value = default_value;
		if (str_value != null) {
			try {
				value = Integer.parseInt(str_value.trim());
			} catch (Exception e) {
				value = default_value;
			}
		}
		return value;
	}

	/**
	 * 获取request中的整数参数, 取值必须是candidates中的某一项
	 *
	 * @param key 参数名
	 * @param default_value 默认值
	 * @param candidates 候选值, 不在候选里的会被滤掉. 如果为空则不限制
	 * @return 整数
	 */
	public int getInt(String key, int default_value, int[] candidates) {
		String str_value = request.getParameter(key);
		int value = default_value;
		if (str_value != null) {
			try {
				value = Integer.parseInt(str_value.trim());
				Arrays.sort(candidates);
				if (Arrays.binarySearch(candidates, value) < 0)
					value = default_value;
			} catch (Exception e) {
				value = default_value;
			}
		}
		return value;
	}

	/**
	 * 获取request中的整数参数, 取值必须在min和max之间
	 *
	 * @param key 参数名
	 * @param default_value 默认值
	 * @param min 最小值(含)
	 * @param max 最大值(含)
	 * @return 整数
	 */
	public int getInt(String key, int default_value, int min, int max) {
		String str_value = request.getParameter(key);
		int value = default_value;
		if (str_value != null) {
			try {
				value = Integer.parseInt(str_value.trim());
				if (value < min)
					value = min;
				else if (value > max)
					value = max;
			} catch (Exception e) {
				value = default_value;
			}
		}
		return value;
	}

	/**
	 * 获取request中的长整数参数, 如果为空则使用0L
	 *
	 * @param key 参数名
	 * @return 整数
	 */
	public long getLong(String key) {
		String str_value = request.getParameter(key);
		if (str_value != null) {
			try {
				return Long.parseLong(str_value.trim());
			} catch (Exception e) {
				return 0L;
			}
		}
		return 0L;
	}

	/**
	 * 获取request中的长整数参数, 如果为空则使用默认值
	 *
	 * @param key 参数名
	 * @param default_value 默认值
	 * @return 长整数
	 */
	public long getLong(String key, long default_value) {
		String str_value = request.getParameter(key);
		long value = default_value;
		if (str_value != null) {
			try {
				value = Long.parseLong(str_value.trim());
			} catch (Exception e) {
				value = default_value;
			}
		}
		return value;
	}

	/**
	 * 获取request中的长整数数组, 如果为空则返回默认数组
	 *
	 * @param key 参数名
	 * @param default_array 默认数组
	 * @param candidates 候选值, 如果为空则无限制, can be null if there is no limit
	 * @return 长整数数组
	 */
	public long[] getLongArray(String key, long[] default_array,
							   long[] candidates) {
		String[] array = request.getParameterValues(key);
		if (array == null || array.length == 0) { return default_array; }

		long[] longs = new long[array.length];
		int pos = 0;
		if (candidates != null) {
			Arrays.sort(candidates);
			for (int i = 0; i < array.length; i++) {
				long value = 0L;
				try {
					value = Long.parseLong(array[i]);
				} catch (NumberFormatException e) {
					continue;
				}

				if (Arrays.binarySearch(candidates, value) >= 0) {
					longs[pos] = value;
					pos++;
				}
			}
		} else {
			for (int i = 0; i < array.length; i++) {
				long value = 0;
				try {
					value = Long.parseLong(array[i]);
				} catch (NumberFormatException e) {
					continue;
				}

				longs[pos] = value;
				pos++;
			}
		}

		return ArrayUtils.subarray(longs, 0, pos);
	}

	/**
	 * 获取request中的unicode字符串参数, 默认为空字符串
	 *
	 * @param key 参数名
	 * @return 字符串
	 */
	public String getUnicode(String key) {
		return getUnicode(key, StaticConfig.get("encoding"), StaticConfig.get("server-encoding"));
	}

	/**
	 * 获取request中的字符串参数, 自动转换为指定的编码, 默认为空字符串
	 *
	 * @param key 参数名
	 * @param encoding 参数编码
	 * @param serverEncoding WEB容器编码
	 * @return 字符串
	 */
	public String getUnicode(String key, String encoding, String serverEncoding) {
		return getUnicode(key, encoding, serverEncoding, "");
	}

	/**
	 * 获取request中的字符串参数, 自动转换为指定的编码
	 *
	 * @param key 参数名
	 * @param encoding 参数编码
	 * @param serverEncoding WEB容器编码
	 * @param default_value 默认值
	 * @return 字符串
	 */
	public String getUnicode(String key, String encoding,
							 String serverEncoding, String default_value) {
		if (serverEncoding.equals(encoding)) { return get(key, default_value); }

		try {
			byte[] tmp = get(key).getBytes(serverEncoding);
			return new String(tmp, encoding);
		} catch (Exception e) {
			return default_value;
		}
	}

	/**
	 * 获取request中的字符串参数, 默认为空字符串
	 *
	 * @param key 参数名
	 * @return 字符串
	 */
	public String get(String key) {
		return get(key, "");
	}

	/**
	 * 获取request中的字符串参数
	 *
	 * @param key 参数名
	 * @param default_value 默认值
	 * @return 字符串
	 */
	public String get(String key, String default_value) {
		String value = request.getParameter(key);
		if (value == null) {
			value = default_value;
		}
		return value.trim();
	}

	/**
	 * 获取request中的字符串参数, 如果不在预设的candidates里面, 则返回默认值
	 *
	 * @param key 参数名
	 * @param default_value 默认值
	 * @param candidates 候选值
	 * @return 字符串
	 */
	public String get(String key, String default_value, String[] candidates) {
		String value = request.getParameter(key);
		if (value == null) {
			value = default_value;
		} else {
			if (!Arrays.asList(candidates).contains(value))
				value = default_value;
		}
		return value.trim();
	}
}
