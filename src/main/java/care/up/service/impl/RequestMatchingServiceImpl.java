package care.up.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import care.up.config.webSocket.WSService;
import care.up.dto.ConsultationRequestDTO;
import care.up.dto.ProfessionalDTO;
import care.up.dto.RequestMatchingDTO;
import care.up.dto.UserDTO;
import care.up.message.SMSClass;
import care.up.model.ConsultationRequest;
import care.up.model.Professional;
import care.up.model.RequestMatching;
import care.up.model.User;
import care.up.repository.ConsultationRequestRepository;
import care.up.repository.ProfessionalRepository;
import care.up.repository.RequestMatchingRepository;
import care.up.service.ConsultationRequestService;
import care.up.service.RequestMatchingService;
import care.up.service.SmsService;

@Service
public class RequestMatchingServiceImpl implements RequestMatchingService {

	private static SMSClass sms = new SMSClass(null, "Vous avez une nouvelle demande de soins sur CareUp");


	@Autowired
	ProfessionalRepository professionalRepository;

	@Autowired
	SmsService smsService;

	@Autowired
	RequestMatchingRepository matchingRepository;

	@Autowired
	ConsultationRequestRepository consultationRequestRepository;

	@Autowired
	WSService wsService;

	@Override
	public List<Professional> MatchingRequestWithProfessional(ConsultationRequest req) {

		getListOfProfessional(req).forEach(prof -> {
			RequestMatching requestMatching = new RequestMatching();
			requestMatching.setConsultationRequest(req);
			requestMatching.setProfessional(prof);
			if (matchingRepository.save(requestMatching) != null) {
				try {
					wsService.sendRequestNotificationToUser(prof.getPhoneNumber(), requestMatching.getId());
					sms.setRecipients("+216" + prof.getPhoneNumber());
					smsService.sendSMS(sms);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		return getListOfProfessional(req);
	}


	private List<Professional> getListOfProfessional(ConsultationRequest req) {

		Set<Professional> res = new HashSet<Professional>();
		if (!listFilled(res)) {
			res.addAll(professionalRepository
					.findByProfessionAndAddressCountryAndAddressDelegationAndAddressState(
							req.getType(),
							req.getPatient().getAddress().getCountry(),
							req.getPatient().getAddress().getDelegation(),
							req.getPatient().getAddress().getState(),
							PageRequest.of(0, (20 - res.size())))
					.toSet());
		}
		if (!listFilled(res)) {
			res.addAll(
					professionalRepository
							.findByProfessionAndAddressCountryAndAddressDelegation(req.getType(),
									req.getPatient().getAddress().getCountry(),
									req.getPatient().getAddress().getDelegation(), PageRequest.of(0, (20 - res.size())))
							.toSet());
		}
		if (!listFilled(res)) {
		res.addAll(
				professionalRepository
							.findByProfessionAndAddressState(req.getType(),
								req.getPatient().getAddress().getState(), PageRequest.of(0, (20 - res.size())))
							.toSet());
	}
System.out.print(res.size());
		return res.stream().collect(Collectors.toList());

	}

	private boolean listFilled(Set<Professional> res) {
		return res.size() > 0;
	}
	
	@Override
	public List<RequestMatching> getAllRequestsMatching(Pageable pageable) {
		return matchingRepository.findAll(pageable).toList();
	}
	@Override
	public List<RequestMatching> getAllRequestByProfessionalId(Long professionalId, Pageable pageable) {
		if (professionalId != null) {
			return matchingRepository.findByProfessionalId(professionalId, pageable).toList();
		}
		return null;
	}
	
	@Override
	public List<RequestMatching> getAllRequestByConsultationRequestId(Long consultationRequestId, Pageable pageable) {
		if (consultationRequestId != null) {
			return matchingRepository.findByConsultationRequestId(consultationRequestId, pageable).toList();
		}
		return null;
	}

	@Override
	public RequestMatching getRequestById(Long requestId) {
		if (requestId != null) {
			Optional<RequestMatching> optional = matchingRepository.findById(requestId);
			if (optional.isPresent()) {
				return optional.get();
			}

		}
		return null;
	}

	@Override
	public boolean refuseRequestByProfessional(Long requestId) {
		matchingRepository.deleteById(requestId);
		return !matchingRepository.existsById(requestId);
	}
	@Transactional
	@Override
	public void delete(long consultationRequestId, long professionalId) {
		matchingRepository.delete( consultationRequestId,  professionalId);
	}


	@Override
	public String MatchingRequestWithProfessionalSpe(long id, long professionalId) {
		Professional prof = professionalRepository.findById(professionalId).get();
		RequestMatching requestMatching = new RequestMatching();
		String res ="";
		Optional<RequestMatching> verif = matchingRepository.findByConsultationRequestIdAndProfessionalId(id, professionalId);
		if (!verif.isPresent()) 
		{		System.out.println("mouch mawjoud");

			requestMatching.setConsultationRequest(consultationRequestRepository.findById(id).get());
			requestMatching.setProfessional(prof);
			if (matchingRepository.save(requestMatching) != null) {
				try {
					wsService.sendRequestNotificationToUser(prof.getPhoneNumber(), requestMatching.getId());
				sms.setRecipients("+216" + prof.getPhoneNumber());
					smsService.sendSMS(sms);
					return
					res= "New";
				} catch (Exception e) {
					System.out.println(e);
				}
			}

		}
		if(verif.isPresent())
		{
			System.out.println("mawjoud");

				System.out.println("try mawjoud");

			sms.setRecipients("+216" + prof.getPhoneNumber());
				smsService.sendSMS(sms);
				return	res= "Rappel";
			
		}
		return res;
		
	}





}


