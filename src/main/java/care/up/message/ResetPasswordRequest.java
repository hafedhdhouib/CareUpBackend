package care.up.message;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ResetPasswordRequest {

	@NotBlank
	private String phoneNumber;

	@NotBlank
	@Size(min = 6, max = 6)
	private int verifCode;

	@NotBlank
	private String password;

}
