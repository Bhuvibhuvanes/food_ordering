package com.FoodOrderingSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.FoodOrderingSystem.model.UsersForm;

@Repository
public interface UsersFormRepo extends JpaRepository<UsersForm, Integer> {

	/*List<StudentForm> findAll();

	StudentForm save(StudentForm student);
	
	@Query("SELECT s FROM StudentForm s WHERE s.email = ?1")
	Optional<StudentForm> findByEmail(String username);

	Optional<StudentForm> findByStudentForm(Object user);*/
	 List<UsersForm> findAll();

	    UsersForm save(UsersForm users);
	    
//	    @Query("SELECT u FROM UsersForm u WHERE suemail = :email")
	    Optional<UsersForm> findByEmail(@Param("email") String email);


//	    Optional<StudentForm> findByForm(Object form);




}
