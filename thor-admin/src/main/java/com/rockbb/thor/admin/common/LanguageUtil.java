package com.rockbb.thor.admin.common;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LanguageUtil {
	private static String[] languages = new String[]{"zh_CN", "zh_TW", "en"};
	private static Map<String, Locale> localeMap;

	static {
		localeMap = new HashMap<String, Locale>();
		for (String language : languages) {
			String[] args = language.split("_");
			if (args.length == 1)
				localeMap.put(language, new Locale(args[0]));
			else
				localeMap.put(language, new Locale(args[0], args[1]));
		}
	}

	public static Locale getLocale(String language) {
		Locale locale = localeMap.get(language);
		return (locale == null) ? localeMap.get(languages[0]) : locale;
	}

}
