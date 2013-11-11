package com.gmail.dengtao.joe.commons.util;

import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XMLUtils {
	
	public static String pretty(String xml) {
		try {
			if (StringUtils.isBlank(xml)) return xml;
			Document doc = DocumentHelper.parseText(xml);
			StringWriter sw = new StringWriter();
			XMLWriter writer = new XMLWriter(sw, OutputFormat.createPrettyPrint());
			writer.write(doc);
			return sw.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String compact(String xml) {
		try {
			if (StringUtils.isBlank(xml)) return xml;
			Document doc = DocumentHelper.parseText(xml);
			StringWriter sw = new StringWriter();
			XMLWriter writer = new XMLWriter(sw, OutputFormat.createCompactFormat());
			writer.write(doc);
			return sw.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}