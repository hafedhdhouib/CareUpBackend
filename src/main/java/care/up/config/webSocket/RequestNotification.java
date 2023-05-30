package care.up.config.webSocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestNotification {

	private String message = "vous avez une nouvelle demande de soins sur CareUp";

	private Long requestId;
}
