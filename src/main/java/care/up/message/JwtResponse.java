package care.up.message;

import java.util.List;

import care.up.dto.UserDTO;
import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String username;
	private UserDTO user;
	private List<String> authorities;

	public JwtResponse(String accessToken, String username, UserDTO user, List<String> authorities) {
		this.token = accessToken;
		this.username = username;
		this.user = user;
		this.authorities = authorities;
	}
}
