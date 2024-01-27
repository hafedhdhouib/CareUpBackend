package care.up.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.modelmapper.ModelMapper;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

import care.up.dto.PatientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Patient extends User {

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "patient")
	private Set<ConsultationRequest> consultationRequests;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "patient")
	private Set<Rating> ratings;

	public static Patient mapToEntity(PatientDTO dto) {
		if (dto != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(dto, Patient.class);
		}
		return null;
	}

	public static void patch(Patient from, Patient to) {
		if (from != null && to != null) {
			to.setBirthDate(from.getBirthDate());
			to.setEmail(from.getEmail());
			to.setName(from.getName());
			to.setGenre(from.getGenre());
			to.setPhoneNumber(from.getPhoneNumber());
			if (to.getAddress() != from.getAddress()) {
				Address.patch(from.getAddress(), to.getAddress());
			}
		}
	}
}
