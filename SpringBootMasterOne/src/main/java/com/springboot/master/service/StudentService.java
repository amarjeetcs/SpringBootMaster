package com.springboot.master.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.master.dto.Validation;
import com.springboot.master.exception.EmailAlreadyExistsException;
import com.springboot.master.exception.EmailAndNumberAlreadyExistException;
import com.springboot.master.exception.NumberAlreadyExistsException;
import com.springboot.master.model.Student;
import com.springboot.master.repository.StudentRepository;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

	@Autowired
	private StudentRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

	// Adding student details logic below
	public Student addStudent(Validation validation)
			throws EmailAlreadyExistsException, NumberAlreadyExistsException, EmailAndNumberAlreadyExistException {

		logger.info("Attempting to add student with details: {}", validation);

		Optional<Student> existingStudentEmailAndNumber = repository.findByEmailAndNumber(validation.getEmail(),
				validation.getNumber());
		if (existingStudentEmailAndNumber.isPresent()) {
			logger.error("Student with email {} and number {} already exists", validation.getEmail(),
					validation.getNumber());
			throw new EmailAndNumberAlreadyExistException("Student with this email and number already exists.");
		}

		Optional<Student> existingStudentByEmail = repository.findByEmail(validation.getEmail());
		if (existingStudentByEmail.isPresent()) {
			logger.error("Student with email {} already exists", validation.getEmail());
			throw new EmailAlreadyExistsException("A student with this email already exists.");
		}

		Optional<Student> existingStudentByNumber = repository.findByNumber(validation.getNumber());
		if (existingStudentByNumber.isPresent()) {
			logger.error("Student with number {} already exists", validation.getNumber());
			throw new NumberAlreadyExistsException("A student with this number already exists.");
		}

		Student student = Student.build(0, validation.getName(), validation.getGender(), validation.getAge(),
				validation.getCity(), validation.getEmail(), validation.getNumber(), validation.getCompany(),
				validation.getSalary(), validation.getCountry());
		Student savedStudent = repository.save(student);
		logger.info("Student added successfully: {}", savedStudent);

		return savedStudent;

	}

	// adding list of student into database logic
	public List<Student> saveAllStudents(@Valid List<Validation> validations)
			throws EmailAlreadyExistsException, NumberAlreadyExistsException, EmailAndNumberAlreadyExistException {

		List<Student> savedStudents = new ArrayList<>();

		for (Validation validation : validations) {
			logger.info("Attempting to add student with details: {}", validation);

			// Check if a student with the same email and number exists
			Optional<Student> existingStudentEmailAndNumber = repository.findByEmailAndNumber(validation.getEmail(),
					validation.getNumber());
			if (existingStudentEmailAndNumber.isPresent()) {
				logger.error("Student with email {} and number {} already exists", validation.getEmail(),
						validation.getNumber());
				throw new EmailAndNumberAlreadyExistException("Student with this email and number already exists.");
			}

			// Check if a student with the same email exists
			Optional<Student> existingStudentByEmail = repository.findByEmail(validation.getEmail());
			if (existingStudentByEmail.isPresent()) {
				logger.error("Student with email {} already exists", validation.getEmail());
				throw new EmailAlreadyExistsException("A student with this email already exists.");
			}

			// Check if a student with the same number exists
			Optional<Student> existingStudentByNumber = repository.findByNumber(validation.getNumber());
			if (existingStudentByNumber.isPresent()) {
				logger.error("Student with number {} already exists", validation.getNumber());
				throw new NumberAlreadyExistsException("A student with this number already exists.");
			}

			// Convert Validation DTO to Student entity
			Student student = Student.build(0, validation.getName(), validation.getGender(), validation.getAge(),
					validation.getCity(), validation.getEmail(), validation.getNumber(), validation.getCompany(),
					validation.getSalary(), validation.getCountry());

			// Save student to the repository
			Student savedStudent = repository.save(student);
			logger.info("Student added successfully: {}", savedStudent);
			savedStudents.add(savedStudent);
		}

		return savedStudents;
	}

	// Updating student details with id logic
	public Student updateStudentDetails(int id, Validation updatedValidation)
			throws EmailAlreadyExistsException, NumberAlreadyExistsException, EmailAndNumberAlreadyExistException {

		logger.info("Attempting to update student with ID: {} and details: {}", id, updatedValidation);

		Student existingStudent = repository.findById(id).orElseThrow(() -> {
			logger.error("Student not found with id: {}", id);
			return new RuntimeException("Student not found with id: " + id);
		});

		// Validation for email and number
		Optional<Student> existingStudentEmailAndNumber = repository.findByEmailAndNumber(updatedValidation.getEmail(),
				updatedValidation.getNumber());
		if (existingStudentEmailAndNumber.isPresent()) {
			logger.error("Student with email {} and number {} already exists", updatedValidation.getEmail(),
					updatedValidation.getNumber());
			throw new EmailAndNumberAlreadyExistException("Student with this email and number already exists.");
		}
//commented logic will also work fine
//		Optional<Student> existingStudentByEmail = repository.findByEmail(updatedValidation.getEmail());
//		if (existingStudentByEmail.isPresent() && existingStudentByEmail.get().getId() == id) {
//			logger.error("Student with email {} already exists", updatedValidation.getEmail());
//			throw new EmailAlreadyExistsException("A student with this email already exists.");
//		}
//
//		Optional<Student> existingStudentByNumber = repository.findByNumber(updatedValidation.getNumber());
//		if (existingStudentByNumber.isPresent() && existingStudentByNumber.get().getId() ==id) {
//			logger.error("Student with number {} already exists", updatedValidation.getNumber());
//			throw new NumberAlreadyExistsException("A student with this number already exists.");
//		}

		// below logic will also work fine
		Optional<Student> existingStudentByEmail = repository.findByEmail(updatedValidation.getEmail());
		if (existingStudentByEmail.isPresent()) {
			logger.error("Student with email {} already exists", updatedValidation.getEmail());
			throw new EmailAlreadyExistsException("A student with this email already exists.");
		}

		Optional<Student> existingStudentByNumber = repository.findByNumber(updatedValidation.getNumber());
		if (existingStudentByNumber.isPresent()) {
			logger.error("Student with number {} already exists", updatedValidation.getNumber());
			throw new NumberAlreadyExistsException("A student with this number already exists.");
		}

		existingStudent.setName(updatedValidation.getName());
		existingStudent.setGender(updatedValidation.getGender());
		existingStudent.setAge(updatedValidation.getAge());
		existingStudent.setCity(updatedValidation.getCity());
		existingStudent.setEmail(updatedValidation.getEmail());
		existingStudent.setNumber(updatedValidation.getNumber());
		existingStudent.setCompany(updatedValidation.getCompany());
		existingStudent.setSalary(updatedValidation.getSalary());
		existingStudent.setCountry(updatedValidation.getCountry());

		Student updatedStudent = repository.save(existingStudent);
		logger.info("Student updated successfully: {}", updatedStudent);

		return updatedStudent;
	}

	// Fetching list of all the student details from the database logic
	public List<Student> getAllStudents() {
		logger.info("Fetching all student details");
		List<Student> students = repository.findAll();
		logger.info("Fetched {} students", students.size());
		return students;
	}

	// Fetching student by id logic
	public Optional<Student> getStudentById(int id) {
		logger.info("Fetching student with ID: {}", id);
		Optional<Student> student = repository.findById(id);
		if (student.isPresent()) {
			logger.info("Student found: {}", student.get());
		} else {
			logger.warn("Student not found with ID: {}", id);
		}
		return student;
	}

	// Deleting student details by id logic
	public boolean deleteById(int id) {
		logger.info("Attempting to delete student with ID: {}", id);
		boolean exists = repository.existsById(id);
		if (exists) {
			repository.deleteById(id);
			logger.info("Student deleted successfully with ID: {}", id);
			return true;
		} else {
			logger.warn("Student not found with ID: {}", id);
			return false;
		}
	}

	// Deleting all student records logic
	public void deleteAllStudents() {
		logger.info("Attempting to delete all student records");
		repository.deleteAll();
		logger.info("All student records have been deleted");
	}

	// Counting total number of students in the table logic
	public int countTotalNumberOfStudentsInTable() {
		logger.info("Counting total number of students in the table");
		int count = repository.countTotalNumberOfStudentsInTable();
		logger.info("Total number of students: {}", count);
		return count;
	}

	// Fetching list of all the student details logic
	public List<Student> listOfStudent() {
		logger.info("Fetching list of all students");
		List<Student> students = repository.listOfStudent();
		logger.info("Fetched {} students", students.size());
		return students;
	}
}