package org.danielmurley.as4j.parsing;

import java.util.List;
import java.util.Map;

import com.structurizr.model.Element;
import com.structurizr.model.Model;
import com.structurizr.model.Person;

public abstract class Mapping {
	
	public abstract void mapFieldsToModel(Model model, Map<String, ?> hm);

	void applyRelationships(Element parent, Model model, List uses) {
		if (uses == null)
			return;
		
		for (int i = 0; i < uses.size(); i++)
		{
			Map<String, ?> m = (Map<String, ?>) uses.get(i);
			Element e = ParsingTools.findRelatedEntityByType(String.valueOf(m.get("name")), model);
			ParsingTools.applyRelationship(parent, e, Fields.get(m, Fields.NAME));
			
		}
		
	}
	
	
}
