package care.up.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import care.up.message.SMSClass;
import care.up.service.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private RestTemplate restTemplate;

	private final String reqUrl = "https://rest.messagebird.com/messages?access_key=A8P9zYpJFayij44Bwykzrp8x0";

	@Override
	public boolean sendSMS(SMSClass sms) {
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			String reqBodyData;
			reqBodyData = new ObjectMapper().writeValueAsString(sms);
			HttpEntity<String> requestEnty = new HttpEntity<>(reqBodyData, header);
			restTemplate.postForEntity(reqUrl, requestEnty, Object.class);
			return true;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return false;
		}

	}

}
