package care.up.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import care.up.enums.ProfessionType;
import care.up.model.Address;
import care.up.model.Patient;
import care.up.model.Professional;
import care.up.model.User;
import care.up.repository.AddressRepository;
import care.up.repository.PatientRepository;
import care.up.repository.ProfessionalRepository;
import care.up.repository.UserRepository;
import care.up.service.UserConversionService;
@Transactional
@Service
public class UserConversionServiceImpl implements UserConversionService {
	
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	ProfessionalRepository professionalRepository;
	@Autowired
	UserRepository  userRepository;
	@Autowired
	AddressRepository addressRepository;


	public Professional convertPatientToProfessional(Patient patient,String diplome,ProfessionType pro) {
		User patients=userRepository.getById(patient.getId());
		System.out.println(patients.getAddress());
		Address adr= addressRepository.getById(patient.getAddress().getId());
		Address adrs=new Address();
		adrs.setCountry(adr.getCountry());
		adrs.setDelegation(adr.getDelegation());
		adrs.setState(adr.getState());
		
		userRepository.deleteById(patient.getId());
		Professional professional=null;
        if (patient != null) {
            professional = new Professional();
            professional.setId(patients.getId()+1);
            professional.setName(patients.getName());
            professional.setBirthDate(patients.getBirthDate());
            professional.setPhoneNumber(patients.getPhoneNumber());
            professional.setUsername(patients.getUsername());
            professional.setEmail(patients.getEmail());
            professional.setPassword(patients.getPassword());
            professional.setGenre(patients.getGenre());
            professional.setVerifCode(patients.getVerifCode());
            professional.setActif(patients.getActif());
            professional.setVerified(patients.getVerified());
            professional.setAbonnements(patients.getAbonnements());
            professional.setAddress(adrs);

            // Spécifique à la classe Professional
            professional.setProfession(pro); // Définir votre valeur par défaut ou la logique appropriée
            professional.setDiploma(diplome); // Définir votre valeur par défaut ou la logique appropriée
            professional.setTourArea(0); // Définir votre valeur par défaut ou la logique appropriée
            professional.setConsultationRequests(null); // Définir votre valeur par défaut ou la logique appropriée
            professional.setRatings(null); // Définir votre valeur par défaut ou la logique appropriée
            System.out.print(professional.getAddress());
            return professionalRepository.save(professional);

        }
		return professional;
    }


	
}
