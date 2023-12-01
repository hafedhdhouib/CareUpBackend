package care.up.dto;

import lombok.Data;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import care.up.model.Abonnement;
import lombok.EqualsAndHashCode;

@Data
public class AbonnementDTO {
	private Long id;

	private String title;
	private String description;
	private int prix;
	private int remise;
	private int nb_jour;


	public static AbonnementDTO mapToDTO(Abonnement entity)
	{
		ModelMapper modelMapper = new ModelMapper();
	return modelMapper.map(entity, AbonnementDTO.class);


	}
	
}
