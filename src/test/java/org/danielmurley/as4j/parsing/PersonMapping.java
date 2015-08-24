package org.danielmurley.as4j.parsing;

import static org.danielmurley.as4j.parsing.Fields.DESCRIPTION;
import static org.danielmurley.as4j.parsing.Fields.NAME;
import static org.danielmurley.as4j.parsing.Fields.get;

import java.util.List;
import java.util.Map;

import com.structurizr.model.Model;
import com.structurizr.model.Person;

public class PersonMapping extends Mapping {

	@Override
	public void mapFieldsToModel(Model model, Map<String, ?> hm) {
		Person person = model.addPerson(get(hm, NAME), get(hm, DESCRIPTION));
		applyRelationships(person, model, (List) hm.get("uses"));
	}
	
}
