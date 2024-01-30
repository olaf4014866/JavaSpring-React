package ar.com.mrdev.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import static ar.com.mrdev.app.user.User.ROLE_MANAGER;


@RestController
public class UserController {

	@Autowired UserService userService;

	/**
	 * To update the profile without modifying the
	 * password (if it's not provided) and  without modifying the
	 * user roles (if the authenticated user isn't a Manager)
	 */
	@PutMapping("/api/users/{id}/profile")
	@PreAuthorize("hasRole('ROLE_MANAGER') or #user?.email == authentication?.name")
	public User updateProfile(HttpServletRequest request, @PathVariable Long id, @Validated @RequestBody User user) {
		return userService.updateProfile(id, user, request.isUserInRole(ROLE_MANAGER));
	}
}
