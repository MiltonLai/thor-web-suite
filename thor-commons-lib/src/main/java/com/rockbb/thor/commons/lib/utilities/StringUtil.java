package com.rockbb.thor.commons.lib.utilities;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Created by Milton on 2015/8/26 at 20:38.
 */
public class StringUtil
{
    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
    /**
     * 为一个URL地址增加参数, 会自动判断是否使用'?'号连接
     *
     * @param url 链接
     * @param params 参数集合
     * @return 编码之后的带参数链接
     */
    public static String appendParametersToURL(String url, List<String[]> params, String encoding)
    {
        if (url == null) url="";
        if (params == null || params.size() == 0) return url;

        StringBuffer sb = new StringBuffer();
        sb.append(url);
        char joiner;
        if (url.indexOf('?') == -1)
            joiner = '?';
        else
            joiner = '&';

        for (String[] pair: params)
        {
            if (pair.length < 2) continue;
            sb.append(joiner);
            sb.append(pair[0]+"=");
            try {
                sb.append(URLEncoder.encode(pair[1], encoding));
            } catch (UnsupportedEncodingException e) {
                // skip
            }
            joiner = '&';
        }
        return sb.toString();
    }

    /**
     * 合并一個字符串數組, 用預置的字符做分隔
     *
     * @param collection
     * @param separator
     * @return
     */
    public static String implode(Collection<String> collection, String separator) {
        if (collection == null)
            return null;
        if (collection.size() == 0)
            return "";

        StringBuffer sb = new StringBuffer();
        Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) sb.append(separator);
        }
        return sb.toString();
    }

    /**
     * 合并一個字符串數組, 用預置的字符做分隔
     *
     * @param array
     * @param separator
     * @return
     */
    public static String implode(String[] array, String separator) {
        if (array == null)
            return null;
        if (array.length == 0)
            return "";
        if (array.length == 1)
            return array[0];

        StringBuffer sb = new StringBuffer();
        sb.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            sb.append(separator);
            sb.append(array[i]);
        }
        return sb.toString();
    }

    public static String shorten(String str, int length) {
        if (StringUtils.isEmpty(str) || str.length() <= length) return str;
        return str.substring(0, length);
    }

    public static boolean isSingleByte(char c) {
        int k = 0x80; // 如果大于则为双字节
        return c / k == 0 ? true : false;
    }

    /**
	 * 姓名遮盖
	 */
	public static String maskRealName(String realName) {
        if (StringUtils.isNoneBlank(realName) && realName.length() > 1) {
            char[] chars = realName.toCharArray();
            for (int i= 1; i < chars.length; i++) {
                chars[i] = '*';
            }
            return String.valueOf(chars);
		}
		return realName;
	}

    /**
     * 手机号遮盖
     */
    public static String maskCellphone(String cellphone) {
        if (StringUtils.isNoneBlank(cellphone) && cellphone.length() >= 11) {
            char[] chars = cellphone.toCharArray();
            for (int i= 3; i < 7; i++) {
                chars[i] = '*';
            }
            return String.valueOf(chars);
        }
        return cellphone;
    }

    /**
	 * 身份证号遮盖
	 */
	public static String maskIdCardNumber(String idCardNumber) {
		if (StringUtils.isNoneBlank(idCardNumber) && idCardNumber.length() >= 14) {
            char[] chars = idCardNumber.toCharArray();
            for (int i= 6; i < 12; i++) {
                chars[i] = '*';
            }
            return String.valueOf(chars);
        }
        return idCardNumber;
	}

    public static void main(String[] args) {

    }
}
