package care.up.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.modelmapper.ModelMapper;

import care.up.dto.AbonnementDTO;
import care.up.dto.ChronicDiseaseDTO;
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
public class Abonnement extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private int prix;
	
	private int nb_jour;
	@Column(columnDefinition = "int default 0")
	private int remise;
	
    @OneToMany(mappedBy = "user")
    Set<AbonnementUser> users;

	public static Abonnement mapToEntity(AbonnementDTO dto) {
		if (dto != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(dto, Abonnement.class);
		}
		return null;
	}

}
