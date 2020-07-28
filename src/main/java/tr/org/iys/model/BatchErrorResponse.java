package tr.org.iys.model;

import java.util.ArrayList;
import java.util.List;

public class BatchErrorResponse {
	public BatchErrorResponse() {
		super();
	}

	private List<Err> errors = new ArrayList<>();

	public BatchErrorResponse(List<Err> errors) {
		super();
		this.errors = errors;
	}

	public List<Err> getErrors() {
		return errors;
	}

	public void setErrors(List<Err> errors) {
		this.errors = errors;
	}
	
}
