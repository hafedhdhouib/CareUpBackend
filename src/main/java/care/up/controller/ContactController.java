package care.up.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.message.ContactForm;
import care.up.service.MailService;

@RequestMapping("/contact")
@RestController
@CrossOrigin
public class ContactController {

	@Autowired
	MailService mailService;

	@PostMapping("send-contact")
	public ResponseEntity<Boolean> sendContact(@RequestBody ContactForm dto) {
		Boolean res = mailService.sendMail(dto);
		if (res) {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}

}
