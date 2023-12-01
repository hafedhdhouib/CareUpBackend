package care.up.message;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequest {

	@NotBlank
	@Size(min = 3, max = 50)
	private String username;

	@NotBlank
	private String password;

}
