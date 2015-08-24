package org.danielmurley.as4j.parsing;

import java.util.List;
import java.util.Map;

import com.structurizr.model.Element;
import com.structurizr.model.Location;
import com.structurizr.model.Model;
import com.structurizr.model.Person;

public class ExternalPersonMapping extends Mapping {

	@Override
	public void mapFieldsToModel(Model model, Map<String, ?> hm) {
		Person person = model.addPerson(Location.External, String.valueOf(hm.get("name")), String.valueOf(hm.get("desc")));
		
		applyRelationships(person, model, (List) hm.get("uses"));
	}

	

	

	

}
