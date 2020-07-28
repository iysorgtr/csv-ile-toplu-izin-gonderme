package tr.org.iys.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class CSVFileContent extends CSVBean {
	@CsvBindByName(column = "type")
	@CsvBindByPosition(position = 0)
	private String type;
	@CsvBindByName(column = "source")
	@CsvBindByPosition(position = 1)
	private String source;
	@CsvBindByName(column = "recipient")
	@CsvBindByPosition(position = 2)
	private String recipient;
	@CsvBindByName(column = "status")
	@CsvBindByPosition(position = 3)
	private String status;
	@CsvBindByName(column = "consentDate")
	@CsvBindByPosition(position = 4)
	private String consentDate;
	@CsvBindByName(column = "recipientType")
	@CsvBindByPosition(position = 5)
	private String recipientType;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

}