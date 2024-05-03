package com.FoodOrderingSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodOrderingSystem.model.CanteenManager;

@Repository
public interface CanteenManagerRepo extends JpaRepository<CanteenManager,Integer>{

	Optional<CanteenManager> findById(int id);

	Optional<CanteenManager> getCanteenManagerById(int id);

	Optional<CanteenManager> findByEmail(String email);




}
