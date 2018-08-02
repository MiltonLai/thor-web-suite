package com.rockbb.thor.commons.lib.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Global config filled by application.xml
 *
 * Created by Milton on 2015/12/10 at 19:03.
 */
public class StaticConfig
{
	private static final Logger logger = LoggerFactory.getLogger(StaticConfig.class);

	// 静态配置变量
	private static final Map<String, Object> configs = loadConfig();

	private StaticConfig() {}

	private static Map<String, Object> loadConfig()
	{
		Map<String, Object> configs = new HashMap<>();
		try
		{
			InputStream is = StaticConfig.class.getResourceAsStream("/application.xml");
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(new InputSource(is));

			// 初始化静态配置变量
			NodeList nodeList = doc.getElementsByTagName("constant");
			for(int i=0; i<nodeList.getLength() ; i++)
			{
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element)node;
					String value = element.getAttribute("value");
					String type = element.getAttribute("type");
					if (type.equals("int"))
					{
						configs.put(
								element.getAttribute("name"), 
								Integer.parseInt(value));
					}
					else if (type.equals("string"))
					{
						configs.put(
								element.getAttribute("name"), 
								value);
					}
					else if (type.equals("array"))
					{
						configs.put(
								element.getAttribute("name"), 
								value.split("\\s*,\\s*"));
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.info("Error occurs in parsing application.xml");
			logger.debug(e.getMessage(), e);
		}
		return configs;
	}

	public static Map<String, Object> getConfigs() {return configs;}

	public static String get(String key) {
			if(configs.get(key)!=null) return (String)configs.get(key);
			return null;
	}

	public static int getInt(String key) {
		if(configs.get(key)!=null) return (Integer)configs.get(key);
		return -1;
	}

	public static String[] getArray(String key) {return (String[])configs.get(key);}

	public static boolean inDevelopment() {return getInt("in_development") == 1;}
}
