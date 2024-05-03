package com.FoodOrderingSystem.service;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.FoodOrderingSystem.model.Shop;
import com.FoodOrderingSystem.model.ShopNotFoundException;
import com.FoodOrderingSystem.repository.ShopRepository;

import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.io.SerialException;

@Service
public class ShopService {
	@Autowired
	ShopRepository shoprepository;

	public Shop createshop(Shop shop) {
		return shoprepository.save(shop);
	}

	public List<Shop> getshop() {
		List<Shop> sop = shoprepository.findAll();
		return sop;
	}

	public Shop getshopbyId(int id) throws ShopNotFoundException {
		Optional<Shop> sop = shoprepository.findById(id);
		if (sop.isPresent()) {
			Shop sp = sop.get();
			return sp;
		} else {
			throw new ShopNotFoundException("Given data is not found");
		}
	}

//	public void updateshopByid(Shop shops, Map<String, Object> fields)
//			throws IllegalAccessException, NoSuchFieldException, SerialException, SQLException, IOException {
//		for (Map.Entry<String, Object> entry : fields.entrySet()) {
//
//			String fieldName = entry.getKey();
//			Object value = entry.getValue();
//
//			try {
//				Field field = shops.getClass().getDeclaredField(fieldName);
//				field.setAccessible(true);
//
//				if ("file".equals(fieldName)) { // Check if the field is 'file'
//					MultipartFile file = (MultipartFile) value;
//					if (!file.isEmpty()) {
//						byte[] photoBytes = file.getBytes();
//						Blob photoBlob = new SerialBlob(photoBytes);
//						field.set(shops, photoBlob);
//					} else {
//						field.set(shops, null);
//					}
//				} else if (field.getType() == LocalDate.class && value instanceof String) {
//					// Convert String to LocalDate
//					LocalDate newValue = LocalDate.parse((String) value, DateTimeFormatter.ISO_DATE);
//					field.set(shops, newValue);
//				} else if (field.getType() == long.class && value instanceof String) {
//					// Convert String to long
//					long newValue = Long.parseLong((String) value);
//					field.set(shops, newValue);
//				} else {
//					field.set(shops, value);
//				}
//			} catch (NoSuchFieldException e) {
//				// Handle the case where the field doesn't exist
//				System.out.println("Field '" + fieldName + "' does not exist in CanteenManager class.");
//			}
//		}
//	}

	public void deleteshopbyid(int id) throws ShopNotFoundException {
		Optional<Shop> shops=shoprepository.findById(id);
		if(shops.isPresent()) {
			shoprepository.deleteById(id);
		}else {
			throw new ShopNotFoundException("Given data is not found");
		}
		
	}

	public Shop updateshopByid(int id, Shop shop) throws ShopNotFoundException {
		Optional<Shop> sop=shoprepository.findById(shop.getId());
		if(sop.isPresent()) {
			Shop shops=sop.get();
			return shops;
		}else {
			throw new ShopNotFoundException("Given data is not found");
		}
		
	}

	public Shop createshop(MultipartFile image, String name, String reviews) throws java.io.IOException, javax.sql.rowset.serial.SerialException, SQLException {
		Shop sh=new Shop();
		sh.setName(name);
		sh.setReviews(reviews);
		sh.setRole("MANAGE");
		if(image!=null && ! image.isEmpty()) {
			byte[] photoBytes = image.getBytes();
			Blob photoBlob=new SerialBlob(photoBytes);
			sh.setImage(photoBlob);	
		}
		return shoprepository.save(sh);
	}

	

}
