package tr.org.iys.model;

import java.util.ArrayList;
import java.util.List;

public class Err {
	
	private String index;
	private String code;
	private List<String> location = new ArrayList<>();
	private String value;
	private String message;
	
	public Err() {
		super();
	}
	
	public Err(String index, String code, List<String> location, String value, String message) {
		super();
		this.index = index;
		this.code = code;
		this.location = location;
		this.value = value;
		this.message = message;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
		this.location = location;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
