package com.FoodOrderingSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.FoodOrderingSystem.model.CanteenManager;
import com.FoodOrderingSystem.model.UsersForm;
import com.FoodOrderingSystem.repository.CanteenManagerRepo;
import com.FoodOrderingSystem.repository.UserInfoRepository;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	@Autowired
	private UserInfoRepository studentRepository;

	@Autowired
	private CanteenManagerRepo canteenManagerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UsersForm> studentInfo = studentRepository.findByEmail(email);
		if (studentInfo.isPresent()) {
			return new UserInfoUserDetails(studentInfo.get());
		}

		Optional<CanteenManager> canteenManagerInfo = canteenManagerRepository.findByEmail(email);
		if (canteenManagerInfo.isPresent()) {
			return new UserInfoUserDetails(canteenManagerInfo.get());
		}

		throw new UsernameNotFoundException("User not found with email: " + email);
	}
}
