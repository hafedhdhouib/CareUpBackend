package care.up.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import care.up.dto.HistoriqueProfessionalDTO;
//import care.up.dto.HistoriqueSmsDTO;
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
public class HistoriqueProfessional extends AbstractBaseEntity{

    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotNull
	private String content; /* accepter  refuser accepter_par_un_autre */
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Professional professional;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ConsultationRequest consultationRequest;

	public static HistoriqueProfessional mapToEntity(HistoriqueProfessionalDTO dto) {
		if (dto != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(dto, HistoriqueProfessional.class);
		}
		return null;
	}
}
