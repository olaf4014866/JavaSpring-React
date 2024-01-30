package ar.com.mrdev.app.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	final UserRepository userRepository;

	@PreAuthorize("hasRole('ROLE_MANAGER') or #user?.email == authentication?.name")
	public User updateProfile(Long id, User user, boolean updateAsManager) {
		User userEntity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		if (updateAsManager) {
			// Only Manager can edit roles
			if (user.getRoles()!=null) {
				userEntity.setRoles(user.getRoles());
			}
		}
		if (user.getPassword() != null) {
			userEntity.setAlreadyEncodedPassword(user.getPassword());
		}
		userEntity.setEmail(user.getEmail());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setDescription(user.getDescription());
		return userRepository.save(userEntity);
	}

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
