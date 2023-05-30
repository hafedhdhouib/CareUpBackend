package care.up.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import care.up.dto.ProfessionalDTO;
import care.up.enums.ProfessionType;
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
public class Professional extends User {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private ProfessionType profession;

	@NotNull
	@Column(length = 50, unique = true)
	private String diploma;

	private int tourArea; // in km


	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "professional")
	private Set<ConsultationRequest> consultationRequests;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "professional")
	private Set<Rating> ratings;

	public static Professional mapToEntity(ProfessionalDTO dto) {
		if (dto != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(dto, Professional.class);
		}
		return null;
	}

	public static void patch(Professional from, Professional to) {
		if (from != null && to != null) {
			to.setBirthDate(from.getBirthDate());
			to.setDiploma(from.getDiploma());
			to.setEmail(from.getEmail());
			to.setName(from.getName());
			to.setGenre(from.getGenre());
			to.setPhoneNumber(from.getPhoneNumber());
			to.setProfession(from.getProfession());
			to.setTourArea(from.getTourArea());
			if (to.getAddress() != from.getAddress()) {
				Address.patch(from.getAddress(), to.getAddress());
			}

		}
	}
}
