package care.up.dto;

import java.sql.Date;

import org.modelmapper.*;

import care.up.model.AbonnementUser;
import lombok.Data;
@Data
public class AbonnementUserDTO {
   private AbonnementDTO abonnement;
   private UserDTO user;
   private Date startDate;
   private Date endDate;

   
   public static AbonnementUserDTO mapToDTO(AbonnementUser entity) {
	    ModelMapper modelMapper = new ModelMapper();

	    TypeMap<AbonnementUser, AbonnementUserDTO> typeMap = modelMapper.createTypeMap(AbonnementUser.class, AbonnementUserDTO.class);
	    typeMap.addMapping(src -> src.getUser(), AbonnementUserDTO::setAbonnement);
	    typeMap.addMapping(src -> src.getAbonnement(), AbonnementUserDTO::setUser);
	    typeMap.addMapping(src-> src.getId().getStartDate(), AbonnementUserDTO::setStartDate);
	    typeMap.addMapping(src-> src.getId().getEndDate(), AbonnementUserDTO::setEndDate);


	    return modelMapper.map(entity, AbonnementUserDTO.class);
	}
   /*
   public static AbonnementUserDTO mapToDTO(AbonnementUser entity) {

	ModelMapper modelMapper = new ModelMapper();
	return mapAllObjectsToDTO(entity, modelMapper.map(entity, AbonnementUserDTO.class));
   }
	private static AbonnementUserDTO mapAllObjectsToDTO(AbonnementUser entity, AbonnementUserDTO dto) {
		if (entity.getAbonnement() != null) {
			dto.setabonnementId(AbonnementUserDTO.mapToDTO(entity.getAbonnement()));
			
		}
		if (entity.getUser() != null) {
			dto.setUserId(AbonnementUserDTO.mapToDTO(entity.getUser()));
		}
		return dto;
	}
*/
}
