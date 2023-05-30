package care.up.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import care.up.model.Message;
import lombok.Data;

@Data
public class MessageDTO {

	private Long id;

	@NotNull
	private String content;
	
	private Boolean seen;

	@NotNull
	private UserDTO sender;

	@NotNull
	private UserDTO receiver;

	private LocalDateTime created;

	public static MessageDTO mapToDTO(Message entity) {
		ModelMapper modelMapper = new ModelMapper();
		return mapAllObjectsToDTO(entity, modelMapper.map(entity, MessageDTO.class));
	}

	private static MessageDTO mapAllObjectsToDTO(Message entity, MessageDTO dto) {
		dto.setSender(UserDTO.mapToDTO(entity.getSender()));
		dto.setReceiver(UserDTO.mapToDTO(entity.getReceiver()));
		return dto;
	}

}
