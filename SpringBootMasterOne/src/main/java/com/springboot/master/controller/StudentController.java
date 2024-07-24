package com.springboot.master.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.master.dto.Validation;
import com.springboot.master.exception.EmailAlreadyExistsException;
import com.springboot.master.exception.EmailAndNumberAlreadyExistException;
import com.springboot.master.exception.NumberAlreadyExistsException;
import com.springboot.master.model.Student;
import com.springboot.master.service.StudentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RestControllerAdvice
@RequestMapping("/student")
class StudentController {
	@Autowired
	private StudentService service;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Value("${server.port}")
	private String portName;

	@PostMapping("/addStudent")
	public ResponseEntity<Student> addStudentData(@RequestBody @Valid Validation validation)
			throws EmailAlreadyExistsException, NumberAlreadyExistsException, EmailAndNumberAlreadyExistException {
		logger.debug("studnet data added & calling to service layer");
		return new ResponseEntity<>(service.addStudent(validation), HttpStatus.CREATED);
	}

	@GetMapping("/getRunningPort")
	public ResponseEntity<String> getRunningPort() {
		logger.debug("Displaying the server port information");
		logger.debug("Server is running on port: " + portName);
		return ResponseEntity.ok("Server is running on port: " + portName);
	}

	@GetMapping("/getAllStudents")
	public ResponseEntity<List<Student>> getAllStudents() {
		logger.debug("Fetching list of all the student details from the database");
		List<Student> students = service.getAllStudents();
		if (students.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content if no students found
		}
		return ResponseEntity.ok(students); // 200 OK with the list of students
	}

	@GetMapping("/getStudentById/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
		logger.debug("Featching Student deatils by id");
		Optional<Student> student = service.getStudentById(id);
		if (student.isPresent()) {
			return ResponseEntity.ok(student.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping("updateStudent/{id}")
	public ResponseEntity<Student> updateStudentDetails(@PathVariable("id") int id,
			@RequestBody Student updatedStudent) {
		logger.debug("Updating Student details with given id");
		Optional<Student> existingStudent = service.getStudentById(id);
		if (existingStudent.isPresent()) {
			Student updated = service.updateStudentDetails(id, updatedStudent);
			return ResponseEntity.ok(updated);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
		logger.debug("Deleting student details by ID: " + id);
		boolean isDeleted = service.deleteById(id);
		if (isDeleted) {
			return ResponseEntity.ok("Student deleted with ID: " + id);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with ID: " + id + " not found");
		}
	}

	@DeleteMapping("/deleteAllStudents")
	public ResponseEntity<String> deleteAllStudents() {
		logger.debug("Deleting list of all student details");
		service.deleteAllStudents();
		return ResponseEntity.ok("All student data has been deleted");
	}

	@GetMapping("/getStudentCount")
	public ResponseEntity<String> countTotalNumberOfStudentsInTable() {
		logger.debug("Fetching the total number of students in the table");
		int count = service.countTotalNumberOfStudentsInTable();
		return ResponseEntity.ok("Total number of students: " + count);
	}

	@GetMapping("/listOfStudents")
	public ResponseEntity<List<Student>> listOfStudent() {
		logger.debug("Fetching list of all the students");
		List<Student> students = service.listOfStudent();
		if (students.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content if no students found
		}
		return ResponseEntity.ok(students);
	}

}
