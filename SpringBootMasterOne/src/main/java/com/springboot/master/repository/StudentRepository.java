package com.springboot.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.master.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	@Query("SELECT COUNT(s) FROM Student s WHERE s.email = ?1 AND s.number = ?2")
	int countByEmailAndNumber(String email, String number);

	@Query("SELECT s FROM Student s WHERE s.email = ?1 AND s.number = ?2")
	Optional<Student> findByEmailAndNumber(String email, String number);

	@Query("SELECT s FROM Student s WHERE s.number = ?1")
	Optional<Student> findByNumber(String number);

	@Query("SELECT s FROM Student s WHERE s.email = ?1")
	Optional<Student> findByEmail(String email);

	@Query(value = "select count(*) from Student")
	int countTotalNumberOfStudentsInTable();

	@Query("select s from Student s")
	List<Student> listOfStudent();
	// jpql, hql, native
	// hibernate query language
	// japa persistance query language

}