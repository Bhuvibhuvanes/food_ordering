package com.FoodOrderingSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FoodOrderingSystem.model.UsersForm;


public interface UserInfoRepository extends JpaRepository<UsersForm, Integer> {

	Optional<UsersForm> findByEmail(String email);

}
