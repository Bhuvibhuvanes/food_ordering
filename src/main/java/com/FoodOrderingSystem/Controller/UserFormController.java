package com.FoodOrderingSystem.Controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.FoodOrderingSystem.model.NotFoundException;
import com.FoodOrderingSystem.model.UsersForm;
import com.FoodOrderingSystem.model.UsersFormResponse;
import com.FoodOrderingSystem.repository.UsersFormRepo;
import com.FoodOrderingSystem.service.UsersFormService;

@ControllerAdvice
@RestController
@RequestMapping("/userforms")
@CrossOrigin(origins = "http://localhost:3000")
public class UserFormController {
	@Autowired
	UsersFormService studentService;

	@GetMapping("/{id}")
	public ResponseEntity<UsersFormResponse> getStudentById(@PathVariable int id) {
		UsersForm s = studentService.getStudentById(id);
		UsersFormResponse response = new UsersFormResponse();
		if (s != null && s.getImage() != null) {
			try {
				Blob photoBytes = studentService.getStudentPhotoByStudentId(id);
				if (photoBytes != null && photoBytes.length() > 0) {
					String base64Photo = Base64.encodeBase64String(photoBytes.getBytes(1, (int) photoBytes.length()));
					response = getStudentFormResponse(s);
					response.setImage(base64Photo);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.setDate_Time(s.getDate_time());
			response.setWallet(s.getWallet());
			response.setPassword(s.getPassword());

			System.out.println(s.getPassword() + ": password");
			System.out.println(s.getWallet() + " wallet");
		}
		System.out.println("---------------------" + response + "---------");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<UsersFormResponse>> getAllStudents() throws SQLException {
		List<UsersForm> students = studentService.getAllStudents();
		List<UsersFormResponse> studentResponses = new ArrayList<>();
		for (UsersForm room : students) {
			Blob photoBytes = studentService.getStudentPhotoByStudentId(room.getStudent_id());
			if (photoBytes != null && photoBytes.length() > 0) {
				String base64Photo = Base64.encodeBase64String(photoBytes.getBytes(1, (int) photoBytes.length()));
				UsersFormResponse studentResponse = getStudentFormResponse(room);
				studentResponse.setImage(base64Photo);
				studentResponse.setPassword(room.getPassword());
				studentResponse.setWallet(room.getWallet());
				studentResponses.add(studentResponse);
			}
		}
		return ResponseEntity.ok(studentResponses);
	}

	@PostMapping
	public ResponseEntity<UsersForm> addStudents(@RequestParam("file") MultipartFile file,
			@RequestParam("rfid_Number") long rfid_Number, @RequestParam("First_name") String First_name,
			@RequestParam("Last_name") String Last_name, @RequestParam("department") String department,
			@RequestParam("aadhar_number") String aadhar_number, @RequestParam("mob_number") long mob_number,
			@RequestParam("address") String address, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("wallet") int wallet,
			@RequestParam("date_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date_time)
			throws IOException, SQLException {
		UsersForm s = studentService.addStudent(file, rfid_Number, First_name, Last_name, department, aadhar_number,
				mob_number, address, password, email, date_time, wallet);
		return new ResponseEntity<UsersForm>(s, HttpStatus.CREATED);
	}

	@DeleteMapping("/{student_id}")
	public String deleteStudentForm(@PathVariable("student_id") int student_id) {
		studentService.deleteStudent(student_id);
		return "delete Record";
	}

	@Autowired
	UsersFormRepo studentFormRepo;

	/*@PutMapping("/{id}")
	public ResponseEntity<StudentForm> updateStudentByField(@PathVariable int id,
			@RequestParam(value="rfid_Number",required = false) long rfid_Number, 
			@RequestParam(value="First_name",required = false) String First_name,
			@RequestParam(value="Last_name",required = false) String Last_name,
			@RequestParam(value="department",required = false) String department,
			@RequestParam(value="aadhar_number",required = false) String aadhar_number, 
			@RequestParam(value="mob_number",required = false) long mob_number,
			@RequestParam(value="address",required = false) String address, 
			@RequestParam(value="password",required = false) String password,
			@RequestParam(value="email",required = false) String email,
			@RequestParam(value="date_time",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date_time)
			throws SerialException, SQLException, IOException {
		Optional<StudentForm> optionalStudent = studentFormRepo.findById(id);
		if (!optionalStudent.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		StudentForm student = optionalStudent.get();

		student.setRfid_Number(rfid_Number);
		student.setFirst_name(First_name);
		student.setLast_name(Last_name);
		student.setDepartment(department);
		student.setAadhar_number(aadhar_number);
		student.setMob_number(mob_number);
		student.setAddress(address);
		student.setPassword(password);
		student.setEmail(email);
		student.setDate_time(date_time);

		try {
			return ResponseEntity.ok(studentService.saveStudent(student));
		} catch (Exception e) {
			e.printStackTrace(); // Handle or log the exception appropriately
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}*/
	@PutMapping("/{id}")
	public ResponseEntity<UsersForm> updateStudentByField(@PathVariable int id,
	        @RequestParam(value="rfid_Number", required = false) Long rfid_Number, 
	        @RequestParam(value="First_name", required = false) String First_name,
	        @RequestParam(value="Last_name", required = false) String Last_name,
	        @RequestParam(value="department", required = false) String department,
	        @RequestParam(value="aadhar_number", required = false) String aadhar_number, 
	        @RequestParam(value="mob_number", required = false) Long mob_number,
	        @RequestParam(value="address", required = false) String address, 
	        @RequestParam(value="password", required = false) String password,
	        @RequestParam(value="email", required = false) String email,
	        @RequestParam(value="date_time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date_time)
	        throws SerialException, SQLException, IOException {
	    Optional<UsersForm> optionalStudent = studentFormRepo.findById(id);
	    if (!optionalStudent.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    UsersForm student = optionalStudent.get();

	    if (rfid_Number != null) {
	        student.setRfid_Number(rfid_Number);
	    }
	    if (First_name != null) {
	        student.setFirst_name(First_name);
	    }
	    if (Last_name != null) {
	        student.setLast_name(Last_name);
	    }
	    if (department != null) {
	        student.setDepartment(department);
	    }
	    if (aadhar_number != null) {
	        student.setAadhar_number(aadhar_number);
	    }
	    if (mob_number != null) {
	        student.setMob_number(mob_number);
	    }
	    if (address != null) {
	        student.setAddress(address);
	    }
	    if (password != null) {
	        student.setPassword(password);
	    }
	    if (email != null) {
	        student.setEmail(email);
	    }
	    if (date_time != null) {
	        student.setDate_time(date_time);
	    }

	    try {
	        return ResponseEntity.ok(studentService.saveStudent(student));
	    } catch (Exception e) {
	        e.printStackTrace(); // Handle or log the exception appropriately
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


	private UsersFormResponse getStudentFormResponse(UsersForm room) {
		byte[] photoBytes = null;
		Blob photoBlob = room.getImage();
		if (photoBlob != null) {
			try {
				photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
			} catch (SQLException e) {
				throw new NotFoundException("Error retrieving photo");
			}
		}

		return new UsersFormResponse(room.getStudent_id(), room.getRfid_Number(), room.getFirst_name(),
				room.getLast_name(), room.getDepartment(), room.getAadhar_number(), room.getMob_number(),
				room.getAddress(), room.getEmail(), room.getDate_time());

	}
}
