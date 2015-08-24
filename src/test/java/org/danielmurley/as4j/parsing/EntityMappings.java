package org.danielmurley.as4j.parsing;

import java.util.HashMap;

public class EntityMappings {

	static HashMap<String, Mapping> adapters = new HashMap<String, Mapping>();
	
	static {
		adapters.put("person", new PersonMapping());
		adapters.put("external-person", new ExternalPersonMapping());
		adapters.put("external-system", new ExternalSystemMapping());
		adapters.put("system", new SystemMapping());
	}
	
	
	public static Mapping buildNewMappingForType(String entityName) {
		Mapping m = adapters.get(entityName);
		if (m == null)
			throw new RuntimeException("Was unable to find mapping for type " + entityName);
		return m;
	}

	

}
