package com.rockbb.thor.commons.lib.utilities;

public class StringValidateUtil {
	/** 手机 */
  	private static final String V_CELLPHONE = "^1[34578]\\d{9}$";
	/** 身份证号 */
	private static final String V_IDCARD = "^(\\d{15}|\\d{18}|\\d{17}(\\d|X|x))$";
	/** 日期 */
	private static final String V_YMD = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
	private static final String V_YMD_SHORT = "^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$";
	/** 日期时分 */
	private static final String V_YMDHM = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])\\s([0-1]\\d|2[0-3]):([0-5]\\d)$";
	private static final String V_YMDHM_SHORT = "^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])([0-1]\\d|2[0-3])([0-5]\\d)$";
	/** 日期时分秒 */
	private static final String V_YMDHMS = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])\\s([0-1]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
	private static final String V_YMDHMS_SHORT = "^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])([0-1]\\d|2[0-3])([0-5]\\d)([0-5]\\d)$";

  	public static boolean isCellphone(String str){
  		return str.matches(V_CELLPHONE);
  	}

	public static boolean isIdCard(String str) {
		return str.matches(V_IDCARD);
	}

	public static boolean isYMD(String str) {
		return str.matches(V_YMD);
	}

	public static boolean isYMDHM(String str) {
		return str.matches(V_YMDHM);
	}

	public static boolean isYMDHMS(String str) {
		return str.matches(V_YMDHMS);
	}

	public static boolean isShortYMD(String str) {
		return str.matches(V_YMD_SHORT);
	}

	public static boolean isShortYMDHM(String str) {
		return str.matches(V_YMDHM_SHORT);
	}

	public static boolean isShortYMDHMS(String str) {
		return str.matches(V_YMDHMS_SHORT);
	}

}
