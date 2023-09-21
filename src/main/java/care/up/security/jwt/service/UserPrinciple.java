package care.up.security.jwt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import care.up.model.Patient;
import care.up.model.Professional;
import care.up.model.User;
import lombok.Data;

@Data
public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Boolean actif;

	private Long id;

	private String name;

	private String email;

	private String username;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrinciple(Long id, String name, String email, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;

	}

	public static UserPrinciple build(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (user instanceof Professional && ((Professional) user).getProfession().toString().length()<=14) {
			authorities.add(new SimpleGrantedAuthority("Professional_Role"));
		} else if (user instanceof Patient) {
			authorities.add(new SimpleGrantedAuthority("Patient_Role"));
		}

		return new UserPrinciple(user.getId(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

}
