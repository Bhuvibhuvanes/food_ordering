package com.FoodOrderingSystem.dto.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.FoodOrderingSystem.config.UserInfoUserDetailsService;
//import com.FoodOrderingSystem.model.CanteenManager;
import com.FoodOrderingSystem.model.UsersForm;
//import com.FoodOrderingSystem.repository.CanteenManagerRepo;
import com.FoodOrderingSystem.repository.UsersFormRepo;
import com.FoodOrderingSystem.repository.UserInfoRepository;
import com.FoodOrderingSystem.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		System.out.println(authHeader);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

	private void authenticateUser(Object user, String token, HttpServletRequest request) {
		UserDetails userDetails;
		if (user instanceof UsersForm) {
			userDetails = userDetailsService.loadUserByUsername(((UsersForm) user).getEmail());
		} 
//		else if (user instanceof CanteenManager) {
//			userDetails = userDetailsService.loadUserByUsername(((CanteenManager) user).getEmail());
//		} 
		else {
			throw new IllegalArgumentException("Unsupported user type: " + user.getClass().getName());
		}

		if (jwtService.validateToken(token, userDetails)) {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
	}
}
