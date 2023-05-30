package care.up.service;

import care.up.message.SMSClass;

public interface SmsService {

	public boolean sendSMS(SMSClass sms);
}
