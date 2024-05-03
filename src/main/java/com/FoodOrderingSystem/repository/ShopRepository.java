package com.FoodOrderingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodOrderingSystem.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Integer>{

}
