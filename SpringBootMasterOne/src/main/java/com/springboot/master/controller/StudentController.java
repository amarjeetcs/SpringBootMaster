package com.springboot.master.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import com.springboot.master.dto.Validation;
import com.springboot.master.exception.EmailAlreadyExistsException;
import com.springboot.master.exception.EmailAndNumberAlreadyExistException;
import com.springboot.master.exception.NumberAlreadyExistsException;
import com.springboot.master.model.Student;
import com.springboot.master.service.StudentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RestControllerAdvice
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService service;

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Value("${server.port}")
	private String portName;

	@Operation(summary = "Add a new student", description = "Adds a new student with the provided details", tags = {
			"addStudent" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "409", description = "Conflict - Email and Number already exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@PostMapping("/addStudent")
	public ResponseEntity<Student> addStudentData(@RequestBody @Valid Validation validation) {
		logger.info("Request received to add a new student with details: {}", validation);
		try {
			Student student = service.addStudent(validation);
			logger.info("Student added successfully: {}", student);
			return new ResponseEntity<>(student, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error occurred while adding student: {}", e.getMessage());
			throw e;
		}
	}

	@Operation(tags = {
			"addMultipleStudents" }, summary = "Store multiple students", description = "Stores a list of students in the database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Students successfully stored"),
			@ApiResponse(responseCode = "400", description = "Invalid input data") })
	@PostMapping("/saveAll")
	public ResponseEntity<List<Student>> saveAllStudents(@RequestBody @Valid List<Validation> validation) {
		{
			List<Student> savedStudents = service.saveAllStudents(validation);
			return ResponseEntity.ok(savedStudents);
		}

		
	}

	@Operation(summary = "Update student details", description = "Updates the details of an existing student by ID", tags = {
			"updateStudentById" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the student details", content = @Content(schema = @Schema(implementation = Student.class))),
			@ApiResponse(responseCode = "404", description = "Student not found") })
	@PutMapping("/updateStudent/{id}")
	public ResponseEntity<Student> updateStudentDetails(@PathVariable("id") int id,
			@RequestBody @Valid Validation updatedValidation)
			throws EmailAlreadyExistsException, NumberAlreadyExistsException, EmailAndNumberAlreadyExistException {
		logger.info("Request received to update student with ID: {} and details: {}", id, updatedValidation);
		try {
			Optional<Student> existingStudent = service.getStudentById(id);
			if (existingStudent.isPresent()) {
				Student updated = service.updateStudentDetails(id, updatedValidation);
				logger.info("Student updated successfully: {}", updated);
				return ResponseEntity.ok(updated);
			} else {
				logger.warn("Student not found with ID: {}", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			logger.error("Error occurred while updating student: {}", e.getMessage());
			throw e;
		}
	}

	@Hidden
	@GetMapping("/getRunningPort")
	public ResponseEntity<String> getRunningPort() {
		logger.info("Fetching the server port information");
		logger.debug("Server is running on port: {}", portName);
		return ResponseEntity.ok("Server is running on port: " + portName);
	}

	@Operation(tags = {
			"getStudents" }, summary = "Get all students", description = "Fetches a list of all student details from the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of students"),
			@ApiResponse(responseCode = "204", description = "No students found") })
	@GetMapping("/getAllStudents")
	public ResponseEntity<List<Student>> getAllStudents() {
		logger.info("Request received to fetch all students");
		List<Student> students = service.getAllStudents();
		if (students.isEmpty()) {
			logger.info("No students found in the database");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		logger.info("Fetched list of students: {}", students);
		return ResponseEntity.ok(students);
	}

	@Operation(tags = {
			"getStudentsById" }, summary = "Get students By id", description = "Fetching student by id from the database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved the student"),
			@ApiResponse(responseCode = "404", description = "Student not found") })
	@GetMapping("/getStudentById/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
		logger.info("Request received to fetch student with ID: {}", id);
		Optional<Student> student = service.getStudentById(id);
		if (student.isPresent()) {
			logger.info("Fetched student: {}", student.get());
			return ResponseEntity.ok(student.get());
		} else {
			logger.warn("Student not found with ID: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Delete student by ID", description = "Deletes the student details by ID", tags = {
			"deleteStudentById" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Student successfully deleted"),
			@ApiResponse(responseCode = "404", description = "Student not found") })
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
		logger.info("Request received to delete student with ID: {}", id);
		boolean isDeleted = service.deleteById(id);
		if (isDeleted) {
			logger.info("Student deleted successfully with ID: {}", id);
			return ResponseEntity.ok("Student deleted with ID: " + id);
		} else {
			logger.warn("Student not found with ID: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with ID: " + id + " not found");
		}
	}

	@Operation(summary = "Delete students", description = "Deletes all student details", tags = { "deleteStudents" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "All students successfully deleted") })
	@DeleteMapping("/deleteAllStudents")
	public ResponseEntity<String> deleteAllStudents() {
		logger.info("Request received to delete all student details");
		service.deleteAllStudents();
		logger.info("All student data has been deleted");
		return ResponseEntity.ok("All student data has been deleted");
	}

	@Hidden
	@GetMapping("/getStudentCount")
	public ResponseEntity<String> countTotalNumberOfStudentsInTable() {
		logger.info("Request received to count total number of students");
		int count = service.countTotalNumberOfStudentsInTable();
		logger.debug("Total number of students: {}", count);
		return ResponseEntity.ok("Total number of students: " + count);
	}

	@Hidden
	@GetMapping("/listOfStudents")
	public ResponseEntity<List<Student>> listOfStudent() {
		logger.info("Request received to fetch list of all students");
		List<Student> students = service.listOfStudent();
		if (students.isEmpty()) {
			logger.info("No students found in the list");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		logger.info("Fetched list of students: {}", students);
		return ResponseEntity.ok(students);
	}

}