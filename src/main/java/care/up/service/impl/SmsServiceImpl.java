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

	private String reqUrl = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=TWFyem91a2lhZGFtc0BnbWFpbC5jb206aTFPUCt5RGlQNmVZT1FBcnJhZi1RKDQzSw==&from=Care up";
	

	@Override
	public boolean sendSMS(SMSClass sms) {
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			String reqBodyData;
		    reqUrl= reqUrl+"&to="+sms.getRecipients()+"&sms="+sms.getBody();
			reqBodyData = new ObjectMapper().writeValueAsString(sms);
			HttpEntity<String> requestEnty = new HttpEntity<>(reqBodyData, header);
			restTemplate.postForEntity(reqUrl, requestEnty, Object.class);
			return true;
		} catch (JsonProcessingException e) {
			System.out.println("sms");

			e.printStackTrace();
			return false;
		}

	}

}
