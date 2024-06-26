package com.FoodOrderingSystem.model;

import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class FoodInventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int food_id;
	private String name;
	private int amount;
	private boolean isAvailability = false;
	private boolean breakfast;
	private boolean lunch;
	private boolean eveningfood;
	private boolean dinner;
	private boolean alltime;
	private int quantity;

	@Lob
	@JsonIgnore
	private Blob photo;

	@JsonProperty("image")
	private String image;

	@Transient 
	private String photoBase64; 
	
	@ManyToMany(mappedBy = "foodItems")
	@JsonIgnore
    private List<PurchaseOrder> purchaseOrders;

	
	public String getPhotoBase64() {
		return photoBase64;
	}

	public void setPhotoBase64(String photoBase64) {
		this.photoBase64 = photoBase64;
	}

//	@ManyToOne
//	@JoinColumn(name = "canteenManager_id") // Assuming the foreign key column name
////    @JsonBackReference // Mark as "back" side of relationship
//	private CanteenManager canteenManager;

	public FoodInventory(int food_id, String name, int amount, boolean isAvailability, boolean breakfast, boolean lunch,
			boolean eveningfood, boolean dinner, boolean alltime, int quantity, Blob photo) {
		super();
		this.food_id = food_id;
		this.name = name;
		this.amount = amount;
		this.isAvailability = isAvailability;
		this.breakfast = breakfast;
		this.lunch = lunch;
		this.eveningfood = eveningfood;
		this.dinner = dinner;
		this.alltime = alltime;
		this.quantity = quantity;
		this.photo = photo;
	}

	public FoodInventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FoodInventory(int food_id2, String name2, int amount2, Blob photo2, int quantity2, boolean alltime2,
			boolean isAvailability, boolean breakfast2, boolean dinner2, boolean eveningfood2, boolean lunch2) {
		this.food_id = food_id2;
		this.name = name2;
		this.amount = amount2;
		this.photo = photo2;
		this.quantity = quantity2;
		this.alltime = alltime2;
		this.isAvailability = isAvailability;
		this.breakfast = breakfast2;
		this.dinner = dinner2;
		this.eveningfood = eveningfood2;
		this.lunch = lunch2;

	}

	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isAvailability() {
		return isAvailability;
	}

	public void setAvailability(boolean isAvailability) {
		this.isAvailability = isAvailability;
	}

	public boolean isBreakfast() {
		return breakfast;
	}

	public void setBreakfast(boolean breakfast) {
		this.breakfast = breakfast;
	}

	public boolean isLunch() {
		return lunch;
	}

	public void setLunch(boolean lunch) {
		this.lunch = lunch;
	}

	public boolean isEveningfood() {
		return eveningfood;
	}

	public void setEveningfood(boolean eveningfood) {
		this.eveningfood = eveningfood;
	}

	public boolean isDinner() {
		return dinner;
	}

	public void setDinner(boolean dinner) {
		this.dinner = dinner;
	}

	public boolean isAlltime() {
		return alltime;
	}

	public void setAlltime(boolean alltime) {
		this.alltime = alltime;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photo) {
		this.photo = photo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrders;
	}

	public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}

//	public CanteenManager getCanteenManager() {
//		return canteenManager;
//	}
//
//	public void setCanteenManager(CanteenManager canteenManager) {
//		this.canteenManager = canteenManager;
//	}

	@Override
	public String toString() {
		return "FoodInventory [food_id=" + food_id + ", name=" + name + ", amount=" + amount + ", isAvailability="
				+ isAvailability + ", breakfast=" + breakfast + ", lunch=" + lunch + ", eveningfood=" + eveningfood
				+ ", dinner=" + dinner + ", alltime=" + alltime + ", quantity=" + quantity + ", photo=" + photo
				+ ", image=" + image + ", photoBase64=" + photoBase64 + ", purchaseOrders=" + purchaseOrders
//				+ ", canteenManager=" + canteenManager + 
				+"]";
	}

}
