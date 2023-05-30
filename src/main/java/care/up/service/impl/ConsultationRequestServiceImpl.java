package care.up.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.enums.RequestStatus;
import care.up.model.ChronicDisease;
import care.up.model.ConsultationRequest;
import care.up.model.Patient;
import care.up.model.Professional;
import care.up.model.Requirement;
import care.up.model.User;
import care.up.repository.ChronicDiseaseRepository;
import care.up.repository.ConsultationRequestRepository;
import care.up.repository.RequirementRepository;
import care.up.repository.UserRepository;
import care.up.service.ConsultationRequestService;
import care.up.service.NotificationService;
import care.up.service.RequestMatchingService;

@Service
public class ConsultationRequestServiceImpl implements ConsultationRequestService {

	@Autowired
	ConsultationRequestRepository requestRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RequirementRepository requirementRepository;

	@Autowired
	ChronicDiseaseRepository chronicDiseaseRepository;

	@Autowired
	RequestMatchingService matchingService;

	@Autowired
	NotificationService notificationService;

	@Override
	public ConsultationRequest addConsultationRequest(ConsultationRequest consultationRequest) {
		ConsultationRequest res = null;
		if (consultationRequest != null) {
			Optional<User> patient = userRepository.findById(consultationRequest.getPatient().getId());
			if (patient.isPresent()) {
				consultationRequest.setPatient((Patient) patient.get());
			}
			Set<Requirement> requirements = new HashSet<>();
			consultationRequest.getRequirements().forEach(r -> {
				Optional<Requirement> optional = requirementRepository.findById(r.getId());
				if (optional.isPresent()) {
					requirements.add(optional.get());
				}
			});
			consultationRequest.setRequirements(requirements);

			Set<ChronicDisease> chronicDiseases = new HashSet<>();
			consultationRequest.getChronicDiseases().forEach(c -> {
				Optional<ChronicDisease> optional = chronicDiseaseRepository.findById(c.getId());
				if (optional.isPresent()) {
					chronicDiseases.add(optional.get());
				}
			});
			consultationRequest.setChronicDiseases(chronicDiseases);
			consultationRequest.setStatus(RequestStatus.IN_PROGRESS);
			res = requestRepository.save(consultationRequest);
			if (res != null) {
				matchingService.MatchingRequestWithProfessional(res);
			}

			return res;
		}
		return null;
	}

	@Override
	public List<ConsultationRequest> getAllConsultationRequests(Pageable pageable) {
		return requestRepository.findAll(pageable).toList();
	}

	@Override
	public ConsultationRequest getConsultationRequestById(Long id) {
		if (id != null) {
			Optional<ConsultationRequest> request = requestRepository.findById(id);
			if (request.isPresent()) {
				return request.get();
			}
		}
		return null;
	}

	@Override
	public ConsultationRequest editConsultationRequest(ConsultationRequest consultationRequest) {
		if (consultationRequest != null) {
			Optional<ConsultationRequest> request = requestRepository.findById(consultationRequest.getId());
			if (request.isPresent()) {
				ConsultationRequest toEdit = request.get();
				ConsultationRequest.patch(consultationRequest, toEdit);
				Set<Requirement> requirements = new HashSet<>();
				consultationRequest.getRequirements().forEach(r -> {
					Optional<Requirement> optional = requirementRepository.findById(r.getId());
					if (optional.isPresent()) {
						requirements.add(optional.get());
					}
				});
				toEdit.setRequirements(requirements);

				Set<ChronicDisease> chronicDiseases = new HashSet<>();
				consultationRequest.getChronicDiseases().forEach(c -> {
					Optional<ChronicDisease> optional = chronicDiseaseRepository.findById(c.getId());
					if (optional.isPresent()) {
						chronicDiseases.add(optional.get());
					}
				});
				consultationRequest.setChronicDiseases(chronicDiseases);
				return requestRepository.save(toEdit);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteConsultationRequestById(Long id) {
		if (id != null && requestRepository.existsById(id)) {
			requestRepository.deleteById(id);
			return !requestRepository.existsById(id);
		}
		return false;
	}

	@Override
	public List<Professional> testSendNotif(Long reqId) {
		ConsultationRequest request = getConsultationRequestById(reqId);
		return matchingService.MatchingRequestWithProfessional(request);

	}

	@Override
	public ConsultationRequest acceptConsultationRequest(Long professionalId, ConsultationRequest req) {
		if (professionalId != null && req != null && req.getId() != null) {
			Optional<User> optional = userRepository.findById(professionalId);
			Optional<ConsultationRequest> request = requestRepository.findById(req.getId());
			if (optional.isPresent() && request.isPresent() && request.get().getStatus() != RequestStatus.ACCEPTED) {
				ConsultationRequest toAccepted = request.get();
				toAccepted.setStatus(RequestStatus.ACCEPTED);
				toAccepted.setProfessional((Professional) optional.get());
				ConsultationRequest res = requestRepository.save(toAccepted);
				if (res.getStatus() == RequestStatus.ACCEPTED) {
					matchingService.delete(req.getId(), professionalId);
					notificationService.createNotoificationForAcceptedRequest(res);
					return res;
				}
			}
		}
		return null;
	}

	@Override
	public List<ConsultationRequest> getAllByPatientId(Long patientId) {
		if (patientId != null) {
			return requestRepository.findByPatientId(patientId);
		}
		return null;
	}

	@Override
	public List<Professional> getAllProfessionalOfAllRequestOfPatient(Long patientId) {
		if (patientId != null) {
			return requestRepository.findByPatientIdAndStatus(patientId, RequestStatus.ACCEPTED).stream()
					.map(req -> req.getProfessional()).collect(Collectors.toList());
		}
		return new ArrayList<Professional>();
	}

}
