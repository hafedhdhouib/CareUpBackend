package care.up.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import care.up.model.Discussion;
import lombok.Data;

@Data
public class DiscussionDTO {

	private Long id;

	private UserDTO firstUser;

	private UserDTO secondUser;

	private LocalDateTime created;

	private List<MessageDTO> Messages;

	public static DiscussionDTO mapToDTO(Discussion entity) {
		if (entity != null) {
			ModelMapper modelMapper = new ModelMapper();
			return mapAllObjectsToDTO(entity, modelMapper.map(entity, DiscussionDTO.class));
		}
		return null;
	}

	private static DiscussionDTO mapAllObjectsToDTO(Discussion entity, DiscussionDTO dto) {
		if (entity.getMessages() != null) {
			dto.setMessages(entity.getMessages().stream().map(m -> MessageDTO.mapToDTO(m)).collect(Collectors.toList()));
		}
		dto.setFirstUser(UserDTO.mapToDTO(entity.getFirstUser()));
		dto.setSecondUser(UserDTO.mapToDTO(entity.getSecondUser()));
		return dto;
	}
}
