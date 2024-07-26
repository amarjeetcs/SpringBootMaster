package com.springboot.master.controller;

import com.springboot.master.dto.Validation;
import com.springboot.master.exception.EmailAlreadyExistsException;
import com.springboot.master.exception.NumberAlreadyExistsException;
import com.springboot.master.exception.EmailAndNumberAlreadyExistException;
import com.springboot.master.model.Student;
import com.springboot.master.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddStudentData_Success() throws Exception {
        // Arrange
        Validation validation = new Validation();
        validation.setEmail("test@example.com");
        validation.setNumber("1234567890");
        Student student = new Student(); // Initialize as needed
        when(studentService.addStudent(any(Validation.class))).thenReturn(student);

        // Act
        ResponseEntity<Student> response = studentController.addStudentData(validation);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(student, response.getBody());
    }

    

    
}
