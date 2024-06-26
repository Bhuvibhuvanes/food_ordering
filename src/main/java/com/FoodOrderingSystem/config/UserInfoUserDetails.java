package com.FoodOrderingSystem.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import com.FoodOrderingSystem.model.CanteenManager;
import com.FoodOrderingSystem.model.UsersForm;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*public class UserInfoUserDetails implements UserDetails {


    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(StudentForm userInfo) {
        name=userInfo.getEmail();
        password=userInfo.getPassword();
//        authorities= Arrays.stream(userInfo.getRole().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}*/

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserInfoUserDetails implements UserDetails {

	private String username;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserInfoUserDetails(UsersForm studentForm) {

		this.username = studentForm.getEmail();
		this.password = studentForm.getPassword();
		this.authorities = new ArrayList<>();
		if (studentForm.isStudent()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
		} else {
		}
	}

//	public UserInfoUserDetails(CanteenManager canteenManager) {
//		this.username = canteenManager.getEmail();
//		this.password = canteenManager.getPassword();
//		this.authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_CANTEEN_MANAGER"));
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
