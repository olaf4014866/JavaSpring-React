package ar.com.mrdev.app.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.stream.Stream;
import static ar.com.mrdev.app.user.User.ROLE_MANAGER;


@Component
@Slf4j
public class DatabaseLoader implements CommandLineRunner {

	final UserRepository userRepository;
	final Environment env;

	@Override
	public void run(String... strings) throws Exception {

		if (userRepository.count() == 0l) {
			SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("greg", "doesn't matter",
					AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

			Stream.of(
				new User("frodo@local", "Frodo", "Baggins", "Ring bearer", "admin", ROLE_MANAGER),
				new User("bilbo@local", "Bilbo", "Baggins", "Burglar", "test"),
				new User("gf@local", "Gandalf", "the Grey", "Wizard", "admin", ROLE_MANAGER),
				new User("lego@local", "Legolas", "Greenleaf", "Elf prince", "test"),
				new User("sam@local", "Sam", "Gamgee", "The gardener", "test")
			).forEach(user -> {
				log.info("Created {}", userRepository.save(user));
			});

			SecurityContextHolder.clearContext();
		} else {
			log.info("Creation of default users skipped");
		}
	}

	@Autowired
	public DatabaseLoader(
		UserRepository userRepository, Environment env) {
		this.userRepository = userRepository;
		this.env = env;
	}
}
