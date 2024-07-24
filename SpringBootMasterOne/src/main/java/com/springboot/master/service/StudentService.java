package com.springboot.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.master.dto.Validation;
import com.springboot.master.exception.EmailAlreadyExistsException;
import com.springboot.master.exception.EmailAndNumberAlreadyExistException;
import com.springboot.master.exception.NumberAlreadyExistsException;
import com.springboot.master.model.Student;
import com.springboot.master.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

	@Autowired
	private StudentRepository repository;

	public Student addStudent(Validation validation)
			throws EmailAlreadyExistsException, NumberAlreadyExistsException, EmailAndNumberAlreadyExistException {

		Optional<Student> existingStudentEmailAndNumber = repository.findByEmailAndNumber(validation.getEmail(),
				validation.getNumber());
		if (existingStudentEmailAndNumber.isPresent()) {
			throw new EmailAndNumberAlreadyExistException("Student with this email and number is alreay exist");
		}
		Optional<Student> existingStudentByEmail = repository.findByEmail(validation.getEmail());
		if (existingStudentByEmail.isPresent()) {
			throw new EmailAlreadyExistsException("A student with this email already exists.");
		}

		Optional<Student> existingStudentByNumber = repository.findByNumber(validation.getNumber());
		if (existingStudentByNumber.isPresent()) {
			throw new NumberAlreadyExistsException("A student with this number already exists.");
		}

		Student student = Student.build(0, validation.getName(), validation.getGender(), validation.getAge(),
				validation.getCity(), validation.getEmail(), validation.getNumber(), validation.getCompany(),
				validation.getSalary(), validation.getCountry());
		return repository.save(student);
	}

	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	public Optional<Student> getStudentById(int id) {
		return repository.findById(id);
	}

	public Student updateStudentDetails(int id, Student updatedStudent) {
		Student existingStudent = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
		// Update existing student details
		existingStudent.setName(updatedStudent.getName());
		existingStudent.setGender(updatedStudent.getGender());
		existingStudent.setAge(updatedStudent.getAge());
		existingStudent.setCity(updatedStudent.getCity());
		existingStudent.setEmail(updatedStudent.getEmail());
		existingStudent.setNumber(updatedStudent.getNumber());
		existingStudent.setCompany(updatedStudent.getCompany());
		existingStudent.setSalary(updatedStudent.getSalary());
		existingStudent.setCountry(updatedStudent.getCountry());

		// Save the updated student to the database
		return repository.save(existingStudent);
	}

	public boolean deleteById(int id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}

	public void deleteAllStudents() {
		repository.deleteAll();
	}

	public int countTotalNumberOfStudentsInTable() {
		return repository.countTotalNumberOfStudentsInTable();
	}

	public List<Student> listOfStudent() {
		return repository.listOfStudent();
	}
}
