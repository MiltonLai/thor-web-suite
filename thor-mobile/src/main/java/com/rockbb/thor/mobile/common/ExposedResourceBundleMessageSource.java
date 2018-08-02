package com.rockbb.thor.mobile.common;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class ExposedResourceBundleMessageSource extends ResourceBundleMessageSource {

    public Set<String> getBundleKeys(String basename, Locale locale) {
        ResourceBundle bundle = getResourceBundle(basename, locale);
        return bundle.keySet();
    }

    public Map<String, String> getBundleMap(String basename, Locale locale) {
        ResourceBundle bundle = getResourceBundle(basename, locale);
        Set<String> keys = bundle.keySet();
        Map<String, String> bundleMap = new HashMap<>();
        for (String key : keys) {
            final String value = bundle.getString(key);
            bundleMap.put(key, value);
        }
        return bundleMap;
    }
}
