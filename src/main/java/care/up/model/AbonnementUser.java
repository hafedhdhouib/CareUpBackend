package care.up.model;

//import java.sql.Date;
//import java.util.Set;

//import javax.persistence.CascadeType;
//import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.modelmapper.ModelMapper;

import care.up.dto.AbonnementUserDTO;
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
public class AbonnementUser {
    @EmbeddedId
    AbonementUserKey id;

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user")
    User user;
    
    @ManyToOne
    @MapsId("abonnement")
    @JoinColumn(name = "abonnement")
    Abonnement abonnement;
    
    public static AbonnementUser mapToEntity(AbonnementUserDTO dto) {
    	System.out.print(dto);
		if (dto != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(dto, AbonnementUser.class);
		}
    	return null;
    	
    }

    

    
    
    



}
