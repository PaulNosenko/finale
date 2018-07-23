package diploma.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import diploma.entity.User;
import diploma.enums.Role;

public class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(User user){
	return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), mapToGrantedAuthorities(user.getRole()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Role role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

}
