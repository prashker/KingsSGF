package modelTestSam;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GameEvent {
	
	String type;
	HashMap<String,Object> map;
	
	public GameEvent() {
		//Dummy constructor
	}

	public GameEvent(String type, HashMap<String, Object> map) {
		this.type = type;
		this.map = map;
	}
	
	public GameEvent(String type) {
		this.type = type;
		this.map = new HashMap<String, Object>();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HashMap<String, Object> getMap() {
		return map;
	}

	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

	public Object get(String key) {
		return map.get(key);
	}
	
	public void put(String key, Object value) {
		map.put(key, value);
	}
	
	public String toJson() {
		try {
			return JacksonSingleton.getInstance().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String toString() {
		return toJson();
	}

}
