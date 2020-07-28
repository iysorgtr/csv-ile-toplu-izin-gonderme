package tr.org.iys.model;

import java.util.ArrayList;
import java.util.List;

public class BatchSuccessResponse {
	private String requestId;
	private List<SubRequest> subRequests = new ArrayList<>();
	
	public BatchSuccessResponse() {
		super();
	}
	public BatchSuccessResponse(String requestId, List<SubRequest> subRequests) {
		super();
		this.requestId = requestId;
		this.subRequests = subRequests;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public List<SubRequest> getSubRequests() {
		return subRequests;
	}
	public void setSubRequests(List<SubRequest> subRequests) {
		this.subRequests = subRequests;
	}
}
