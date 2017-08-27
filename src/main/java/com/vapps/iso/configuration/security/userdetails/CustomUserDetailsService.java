package com.vapps.iso.configuration.security.userdetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vapps.iso.entity.User;
import com.vapps.iso.repository.UserRepository;
import com.vapps.iso.repository.UserRolesRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRolesRepository userRolesRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Bad credentials");
		}

		List<String> userRoles = userRolesRepository.findRoleByUserName(userName);
		return new CustomUserDetails(user, userRoles);
	}
}
