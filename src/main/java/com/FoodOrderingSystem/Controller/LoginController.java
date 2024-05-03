package com.FoodOrderingSystem.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.FoodOrderingSystem.dto.AuthRequest;
import com.FoodOrderingSystem.model.CanteenManager;
import com.FoodOrderingSystem.model.UsersForm;
import com.FoodOrderingSystem.repository.CanteenManagerRepo;
import com.FoodOrderingSystem.repository.UsersFormRepo;
import com.FoodOrderingSystem.service.JwtService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UsersFormRepo userFormRepo;

	@Autowired
	private CanteenManagerRepo canteenManagerRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public Map<String, Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

			String email = authRequest.getEmail();
			Object user = getUserByEmail(email);

			if (authentication.isAuthenticated() && user != null) {
				String role = determineRole(user);

				String token = jwtService.generateToken(email, role);

				Map<String, Object> response = new HashMap<>();
				response.put("token", token);
				response.put("role", role);
				response.put("id", getIdFromUser(user));

				return response;
			} else {
				throw new UsernameNotFoundException("Invalid user request!");
			}
		} catch (AuthenticationException ex) {
			throw new RuntimeException("Authentication failed: " + ex.getMessage());
		}
	}

	private Object getUserByEmail(String email) {
		Optional<UsersForm> student = userFormRepo.findByEmail(email);
		if (student.isPresent()) {
			return student.get();
		}

		Optional<CanteenManager> canteenManager = canteenManagerRepo.findByEmail(email);
		if (canteenManager.isPresent()) {
			return canteenManager.get();
		}

		throw new UsernameNotFoundException("User not found");
	}

	private String determineRole(Object user) {
		if (user instanceof UsersForm) {
			System.out.println("Student   :");
			return "STUDENT";
		} else if (user instanceof CanteenManager) {
			 CanteenManager manager = (CanteenManager) user;
		        if(manager.getRole().equals("MANAGER")) {
		            return "MANAGER";
		        } else {
		            return "ADMIN";
		        }
		} else {
			throw new IllegalArgumentException("Invalid user type");
		}
	}

	private int getIdFromUser(Object user) {
		if (user instanceof UsersForm) {
			return ((UsersForm) user).getStudent_id();
		} else if (user instanceof CanteenManager) {
			return ((CanteenManager) user).getId();
		} else {
			throw new IllegalArgumentException("Invalid user type");
		}
	}

}
