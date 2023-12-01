package care.up.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;

import care.up.dto.PatientDTO;
import care.up.dto.ProfessionalDTO;
import care.up.dto.UserDTO;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USER")
public class User extends AbstractBaseEntity implements Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 3, max = 50)
	private String name;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@NotNull
	@Column(length = 20)
	private String phoneNumber;

	@NotNull
	@Column(length = 50)
	private String username;

	@NotNull
	@Email
	@Column(length = 40)
	private String email;

	@NotNull
	@Size(min = 6, max = 100)
	private String password;

	@NotNull
	@Size(max = 20)
	private String genre;
	
	@Range(min=111111, max=999999)
	private int verifCode;
	
	private Boolean actif;


	private Boolean verified;
    @OneToMany(mappedBy = "abonnement")
    Set<AbonnementUser> abonnements;


	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	private Address address;

	public static User mapToEntity(UserDTO dto) {
		if (dto != null) {
		ModelMapper modelMapper = new ModelMapper();
		if (dto instanceof ProfessionalDTO) {
			return modelMapper.map(dto, Professional.class);
		} else if (dto instanceof PatientDTO) {
			return modelMapper.map(dto, Patient.class);
		}
	}
		return null;
	}

	public User clone() throws CloneNotSupportedException {
		return (User) super.clone();
	}
}
