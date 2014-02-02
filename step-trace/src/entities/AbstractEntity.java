package entities;

import java.util.Map;

public abstract class AbstractEntity {

	private String id;
	
	public static Map<String, String> linesMap;
	
	public AbstractEntity() {
		//
	}
	
	public AbstractEntity(String entityId) {
		if (!linesMap.get(entityId).startsWith(getEntityName())) {
			throw new RuntimeException("wrong entity name");
		}
		id = entityId;
	}

	public String getLineId() {
		return id;
	}
	
	public abstract String getEntityName();

}
