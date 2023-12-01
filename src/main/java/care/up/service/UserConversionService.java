package care.up.service;

import care.up.enums.ProfessionType;
import care.up.model.Patient;
import care.up.model.Professional;

public interface UserConversionService {
    Professional convertPatientToProfessional(Patient patient,String diplome,ProfessionType pro);
}
