package ar.com.mrdev.app.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SpringDataJpaUserDetailsService implements UserDetailsService {

	private final UserRepository repository;

	@Autowired
	public SpringDataJpaUserDetailsService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		ar.com.mrdev.app.user.User user = this.repository.findByEmail(name);
		if (user != null) {
			log.info("User trying to access... username={}", name);
			return new User(user.getEmail(), user.getPassword(),
				AuthorityUtils.createAuthorityList(user.getRoles()));
		}
		log.info("User not found, username={}", name);
		throw new UsernameNotFoundException("User not found");
	}
}
