package org.danielmurley.as4j.parsing;

import java.util.Map;

public class Fields {

	public static final String DESCRIPTION = "desc";
	public static final String NAME = "name";

	public static String get(Map map, String fieldName)
	{
		return String.valueOf(map.get(fieldName));
	}
	
}
