package care.up.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import care.up.model.Notification;
import lombok.Data;

@Data
public class NotificationDTO {

	private Long id;

	private String content;

	private Boolean seen = false;

	private LocalDateTime created;

	private ProfessionalDTO professional;

	public static NotificationDTO mapToDTO(Notification entity) {
		if (entity != null) {
			ModelMapper modelMapper = new ModelMapper();
			return mapAllObjectsToDTO(entity, modelMapper.map(entity, NotificationDTO.class));
		}
		return null;
	}

	private static NotificationDTO mapAllObjectsToDTO(Notification entity, NotificationDTO dto) {
		dto.setProfessional(ProfessionalDTO.mapToDTO(entity.getProfessional()));
		return dto;
	}
}
