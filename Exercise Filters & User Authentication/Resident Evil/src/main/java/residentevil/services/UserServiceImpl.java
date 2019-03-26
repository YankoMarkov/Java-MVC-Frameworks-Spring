package residentevil.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import residentevil.entities.User;
import residentevil.models.services.UserRoleServiceModel;
import residentevil.models.services.UserServiceModel;
import residentevil.repositories.UserRepository;
import residentevil.repositories.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final ModelMapper modelMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
	}
	
	@Override
	public UserServiceModel saveUser(UserServiceModel userService) {
		User user = this.modelMapper.map(userService, User.class);
		user.setPassword(this.passwordEncoder.encode(userService.getPassword()));
		giveRolesToUser(user);
		try {
			user = this.userRepository.saveAndFlush(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return this.modelMapper.map(user, UserServiceModel.class);
	}
	
	@Override
	public UserServiceModel updateUsersRole(UserServiceModel userService, UserRoleServiceModel userRoleService) {
		User user = this.userRepository.findById(userService.getId()).orElse(null);
		try {
			this.userRoleRepository.findByAuthority(userRoleService.getAuthority())
					.ifPresent(userRole -> user.getAuthorities().add(userRole));
			this.userRepository.saveAndFlush(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return this.modelMapper.map(user, UserServiceModel.class);
	}
	
	@Override
	public UserServiceModel getUserById(String id) {
		User user = this.userRepository.findById(id).orElse(null);
		if (user == null) {
			return null;
		}
		return this.modelMapper.map(user, UserServiceModel.class);
	}
	
	@Override
	public UserServiceModel getUserByUsername(String username) {
		User user = this.userRepository.findByUsername(username).orElse(null);
		if (user == null) {
			return null;
		}
		return this.modelMapper.map(user, UserServiceModel.class);
	}
	
	@Override
	public List<UserServiceModel> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		if (users == null) {
			return new ArrayList<>();
		}
		return users.stream()
				.map(user -> this.modelMapper.map(user, UserServiceModel.class))
				.collect(Collectors.toUnmodifiableList());
	}
	
	private void giveRolesToUser(User user) {
		if (this.userRepository.count() == 0) {
			this.userRoleRepository.findByAuthority("ADMIN")
					.ifPresent(adminRole -> user.getAuthorities().add(adminRole));
			this.userRoleRepository.findByAuthority("MODERATOR")
					.ifPresent(moderatorRole -> user.getAuthorities().add(moderatorRole));
			this.userRoleRepository.findByAuthority("USER")
					.ifPresent(userRole -> user.getAuthorities().add(userRole));
		} else {
			this.userRoleRepository.findByAuthority("USER")
					.ifPresent(userRole -> user.getAuthorities().add(userRole));
		}
	}
}
