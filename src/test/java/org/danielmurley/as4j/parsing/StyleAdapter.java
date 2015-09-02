package org.danielmurley.as4j.parsing;

import com.structurizr.model.Element;
import com.structurizr.model.Person;

public class StyleAdapter {

	public static String getStyle(Element e) {
		if (e instanceof Person)
		{
			return "verticalLabelPosition=bottom;verticalAlign=top;shape=actor";
		}
		return "";
	}

	public static double getWidth(Element e) {
		if (!(e instanceof Person))
		{
			return 150;
		}
		return 80;
	}

	public static double getHeight(Element e) {
		if (!(e instanceof Person))
		{
			return 100;
		}
		return 100;
	}

	
}
