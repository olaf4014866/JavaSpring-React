package ar.com.mrdev.app.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByEmail(String email);

	@Override
	@PreAuthorize("hasRole('ROLE_MANAGER') or #user?.email == authentication?.name")
	User save(@Param("user") User user);

	@Override
	@PreAuthorize("hasRole('ROLE_MANAGER') or #user?.email == authentication?.name")
	void deleteById(@Param("id") Long id);

	@Override
	@PreAuthorize("hasRole('ROLE_MANAGER') or #user?.email == authentication?.name")
	void delete(@Param("user") User user);
}
