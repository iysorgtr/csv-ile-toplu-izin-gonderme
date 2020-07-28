package tr.org.iys.model;

public class ConsentRequest {
	
	private String type;
	private String source;
	private String recipient;
	private String status;
	private String consentDate;
	private String recipientType;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConsentDate() {
		return consentDate;
	}
	public void setConsentDate(String consentDate) {
		this.consentDate = consentDate;
	}
	public String getRecipientType() {
		return recipientType;
	}
	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}

}
