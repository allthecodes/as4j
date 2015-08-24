package org.danielmurley.as4j.parsing;

import java.util.List;
import java.util.Map;

import com.structurizr.model.Location;
import com.structurizr.model.Model;
import com.structurizr.model.SoftwareSystem;

import static org.danielmurley.as4j.parsing.Fields.*;

public class ExternalSystemMapping extends Mapping {

	@Override
	public void mapFieldsToModel(Model model, Map<String, ?> hm) {
		SoftwareSystem system = model.addSoftwareSystem(Location.External, get(hm, NAME), get(hm, DESCRIPTION));
		applyRelationships(system, model, (List) hm.get("uses"));
	}

}
