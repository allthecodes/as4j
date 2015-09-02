package org.danielmurley.as4j.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.structurizr.model.Element;
import com.structurizr.model.Model;

public class NonThreadSafeParsingContext {
	static ArrayList<LaterRelationship> relationshipsToApplyLater = new ArrayList<LaterRelationship>();
	
	
	public static void saveRelationshipForLater(Element parent, String name, String description)
	{
		LaterRelationship lr = new LaterRelationship(parent, name, description);
		NonThreadSafeParsingContext.relationshipsToApplyLater.add(lr);
	}
	
	
	public static void applyAndFlushLaterRelationshipsToModel(Model model)
	{
		
		List<LaterRelationship> relationships = (List<LaterRelationship>) relationshipsToApplyLater.clone();
		relationshipsToApplyLater.clear();
		
		for (LaterRelationship lr : relationships)
		{
			
			System.err.println("Processing Add of " + lr);
			Element e = ParsingTools.findRelatedEntityByType(lr.name, model);
			ParsingTools.applyRelationship(lr.parent, e, lr.name, lr.description);	
		}
		
		for (LaterRelationship lr : relationshipsToApplyLater)
		{
			System.err.println(" Unable to apply relationship " + lr);
		}
	}
	
	static class LaterRelationship
	{
		public LaterRelationship(Element parent, String name, String description) {
			super();
			this.parent = parent;
			this.name = name;
			this.description = description;
		}
		Element parent;
		String name;
		String description;
		@Override
		public String toString() {
			return "LaterRelationship [parent=" + parent + ", name=" + name + ", description=" + description + "]";
		}
	
	
	
	}
}
