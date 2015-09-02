package org.danielmurley.as4j.parsing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import com.structurizr.model.Element;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.Relationship;

public class ParsingTools {

	public static Element findRelatedEntityByType(String name, Model model) {
		Set<Element> elements = model.getElements();
		return elements.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
	}

	
	
	public static void applyRelationship(Element parent, Element releatedElement, String name, String description) {
		
		if (releatedElement == null)
		{
			NonThreadSafeParsingContext.saveRelationshipForLater(parent, name, description);
			return;
		}
		
		try {
			Method m = parent.getClass().getMethod("uses", new Class[] {releatedElement.getClass(), String.class});
			Relationship rel = (Relationship) m.invoke(parent, releatedElement, name);
			if (description != null && description.equals("null"))
				description = "";
			
			rel.setDescription(description);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			throw new RuntimeException(e1);
		}	
	}
	

	
}
