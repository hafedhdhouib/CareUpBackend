package care.up.service;

import care.up.message.ContactForm;

public interface MailService {

	public boolean sendMail(ContactForm contactForm);
}
