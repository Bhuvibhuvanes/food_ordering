package com.FoodOrderingSystem.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.FoodOrderingSystem.model.Shop;
import com.FoodOrderingSystem.model.ShopNotFoundException;
import com.FoodOrderingSystem.service.ShopService;

import lombok.Delegate;

@RestController
@RequestMapping("/shops")
public class ShopController {
	
	@Autowired
	ShopService shopservice;
	
	@PostMapping
	public ResponseEntity<Shop> createshop(@RequestParam("file") MultipartFile file,@RequestParam("name")String name,
			@RequestParam("reviews") String reviews) throws SerialException, IOException, SQLException{
			
		Shop shops=shopservice.createshop(file,name,reviews);
		return new ResponseEntity<Shop>(shops,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Shop>>getshop(){
		List<Shop> shops=shopservice.getshop();
		return new ResponseEntity<List<Shop>>(shops,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Shop> getshopbyId(@PathVariable int id) throws ShopNotFoundException{
		Shop sop=shopservice.getshopbyId(id);
		return new ResponseEntity<Shop>(sop,HttpStatus.OK);
	}
//	@PutMapping("/{id}")
//	public ResponseEntity<Shop> updateByid(@PathVariable("id") int id,
//			@RequestParam(value="name",required = false)String name,
//			@RequestParam(value="reviews",required=false)String reviews){
//		Shop shops=shopservice.updateshopByid(id);
//		return  ResponseEntity.ok(shops);
//	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Shop> updateshopByid(@PathVariable("id") int id, @RequestBody Shop shop) throws ShopNotFoundException {
		Shop sop;
			sop = shopservice.updateshopByid(id,shop);
			return new ResponseEntity<Shop>(sop,HttpStatus.CREATED);
	}
	@DeleteMapping("/{id}")
	public  HttpStatus deletebyid(@PathVariable("id") int id) throws ShopNotFoundException{
	shopservice.deleteshopbyid(id);
		return HttpStatus.OK;
	}
	}
