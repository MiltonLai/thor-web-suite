package com.rockbb.thor.commons.lib.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageBean
{
	public static final String ATTR_KEY = "PAGE_BEAN";

	private Map<String, Object> data = new HashMap<>();
	private Map<String, Object> message = new HashMap<>();
	private List<String> metaExtra = new ArrayList<>();
	private List<String> metaJS = new ArrayList<>();
	private List<String> metaCSS = new ArrayList<>();

	public void destroy()
	{
		if (data != null) data.clear();
		if (message != null) message.clear();
		if (metaExtra != null) metaExtra.clear();
		if (metaJS != null) metaJS.clear();
		if (metaCSS != null) metaCSS.clear();
	}

	public Map<String, Object> getData()
	{
		return data;
	}
	public Map<String, Object> getMessage()
	{
		return message;
	}
	public List<String> getMetaExtra()
	{
		return metaExtra;
	}
	public List<String> getMetaJS()
	{
		return metaJS;
	}
	public List<String> getMetaCSS()
	{
		return metaCSS;
	}

	public void addObject(String key, Object value){data.put(key, value);}
	public void removeObject(String key){data.remove(key);}
	public void addMessage(String key, Object value){message.put(key, value);}
	public void removeMessage(String key){message.remove(key);}
	public void addMetaExtra(String meta) {metaExtra.add(meta);}
	public void addMetaCSS(String css) {metaCSS.add(css);}
	public void addMetaJS(String js) {metaJS.add(js);}
}
