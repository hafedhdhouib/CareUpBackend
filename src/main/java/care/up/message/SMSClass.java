package care.up.message;

import lombok.Data;

@Data
public class SMSClass {

	private String originator = "CareUp";
	private String recipients;
	private String body;
	
	public SMSClass(String recipients, String body) {
		this.recipients = "+216" + recipients;
		this.body = body;
	}
}
