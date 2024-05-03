package com.FoodOrderingSystem.model;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;

@Entity
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private String reviews;
	private String role;
	
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFormJson() {
		return formJson;
	}

	public void setFormJson(String formJson) {
		this.formJson = formJson;
	}

	@Lob
	 private String formJson;
	
	@Lob
	@JsonIgnore
	private Blob image;

	@Transient
	private String photoBase64;

	public Shop() {
		super();
	}

	
	public Shop( String name, String reviews, String role, String formJson, Blob image, String photoBase64) {
		super();
		this.name = name;
		this.reviews = reviews;
		this.role = role;
		this.formJson = formJson;
		this.image = image;
		this.photoBase64 = photoBase64;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReviews() {
		return reviews;
	}

	public void setReviews(String reviews) {
		this.reviews = reviews;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public String getPhotoBase64() {
		return photoBase64;
	}

	public void setPhotoBase64(String photoBase64) {
		this.photoBase64 = photoBase64;
	}

}
