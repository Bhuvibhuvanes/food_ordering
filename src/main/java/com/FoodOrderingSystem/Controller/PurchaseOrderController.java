package com.FoodOrderingSystem.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodOrderingSystem.model.FoodInventory;
import com.FoodOrderingSystem.model.PurchaseOrder;
import com.FoodOrderingSystem.model.UsersForm;
import com.FoodOrderingSystem.repository.FoodInventoryRepo;
import com.FoodOrderingSystem.repository.PurchaseOrderRepo;
import com.FoodOrderingSystem.repository.UsersFormRepo;
import com.FoodOrderingSystem.service.FoodInventoryService;
import com.FoodOrderingSystem.service.PurchaseOrderService;

@ControllerAdvice
@RestController
@RequestMapping("/purchase")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseOrderController {
	@Autowired
	PurchaseOrderService purchaseService;

	@GetMapping
	public ResponseEntity<List<PurchaseOrder>> getPurchaseOrder() {
		List<PurchaseOrder> purchaseorder = purchaseService.getOrder();
		return new ResponseEntity<List<PurchaseOrder>>(purchaseorder, HttpStatus.OK);
	}

	@Autowired
	UsersFormRepo studentRepo;

	@PostMapping
	@Transactional
	public List<PurchaseOrder> addOrders(@RequestBody List<PurchaseOrder> newOrders) {
		List<PurchaseOrder> savedOrders = new ArrayList<>();
		System.out.println("newOrders---" + newOrders.size());

		for (PurchaseOrder newOrder : newOrders) {
			// Retrieve the StudentForm associated with the PurchaseOrder
			Optional<UsersForm> studentOptional = studentRepo.findById(newOrder.getStudentForm().getStudent_id());

			if (studentOptional.isPresent()) {
				UsersForm student = studentOptional.get();

				int currentBalance = student.getWallet();
				int bill = newOrder.getTotalAmount();
				System.out.println(student.getWallet() + "prev wallet-------");
				System.out.println(newOrder.getTotalAmount() + "current amount-------");
				System.out.println(currentBalance - bill);
				System.out.println("current available wallet");

				if (currentBalance >= bill) {
					student.setWallet(currentBalance - bill);
					studentRepo.save(student);
					newOrder.setStudentForm(student);
				} else {
					throw new IllegalArgumentException(
							"Insufficient balance for student with ID: " + student.getStudent_id());
				}
			} else {
				throw new IllegalArgumentException(
						"Student not found with ID: " + newOrder.getStudentForm().getStudent_id());
			}

			// Retrieve the FoodInventory associated with the PurchaseOrder
			Optional<FoodInventory> foodData = foodInventoryRepository.findByFoodId(newOrder.getFood_id());

			if (foodData.isPresent()) {
				FoodInventory foodInventory = foodData.get();
				System.out.println("Before Update :" + foodInventory.getQuantity());

				int prevQuantity = foodInventory.getQuantity();
				int newQuantity = (int) newOrder.getQuantity();

				if (prevQuantity >= newQuantity) {
					foodInventory.setQuantity(prevQuantity - newQuantity);
					foodInventory = foodInventoryRepository.save(foodInventory); // Update the existing record
					System.out.println("After Update :" + foodInventory.getQuantity());
					newOrder.setFoodItems(Arrays.asList(foodInventory)); // Set the food items in PurchaseOrder
				} else {
					throw new IllegalArgumentException(
							"Insufficient Quantity for food with ID: " + foodData.get().getFood_id());
				}
			}

			System.out.println("foodItems :" + foodData);
			System.out.println(newOrder.getStudentForm().getWallet() + "============");

			savedOrders.add(purchaseOrderRepository.save(newOrder));
		}

		return savedOrders;
	}

	@Autowired
	FoodInventoryService foodInventoryService;

	@Autowired
	FoodInventoryRepo foodInventoryRepository;
	@Autowired
	PurchaseOrderRepo purchaseOrderRepository;

	@PutMapping("/{id}")
	public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@PathVariable int id,
			@RequestBody PurchaseOrder purchaseOrder) {
		PurchaseOrder updatedPurchaseOrder = purchaseService.updatePurchaseOrder(id, purchaseOrder);
		return ResponseEntity.ok(updatedPurchaseOrder);
	}

	@DeleteMapping("/{id}")
	public String deleteOrders(@PathVariable int id) {
		purchaseService.deleteOrder(id);
		return "delete record";
	}

	@GetMapping("/{id}")
	public ResponseEntity<PurchaseOrder> getStudentById(@PathVariable int id) {
		PurchaseOrder p = purchaseService.getStudentById(id);
		return new ResponseEntity<PurchaseOrder>(p, HttpStatus.OK);
	}

}
