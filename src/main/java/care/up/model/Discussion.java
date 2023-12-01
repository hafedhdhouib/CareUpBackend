package care.up.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cascade;

import org.hibernate.annotations.CascadeType;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import care.up.dto.DiscussionDTO;
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
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "UniqueUsers", columnNames = { "first_user_id", "second_user_id" }) })
public class Discussion extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private User firstUser;

	@ManyToOne(optional = false)
	private User secondUser;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discussion", orphanRemoval = false)
	@Cascade({CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JsonManagedReference
	private List<Message> messages;

	public static Discussion mapToEntity(DiscussionDTO dto) {
		if (dto != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(dto, Discussion.class);
		}
		return null;
	}

}
