package org.danielmurley.as4j.parsing;

import static org.danielmurley.as4j.parsing.Fields.DESCRIPTION;
import static org.danielmurley.as4j.parsing.Fields.NAME;
import static org.danielmurley.as4j.parsing.Fields.get;

import java.util.List;
import java.util.Map;

import com.structurizr.model.Model;
import com.structurizr.model.SoftwareSystem;

public class SystemMapping extends Mapping {

	@Override
	public void mapFieldsToModel(Model model, Map<String, ?> hm) {
		SoftwareSystem software = model.addSoftwareSystem(get(hm, NAME), get(hm, DESCRIPTION));
		
		applyRelationships(software, model, (List) hm.get("uses"));
	}

}
