package com.springboot.master.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Validation {
    private int id;

    @NotNull(message = "User name should not be null")
    @NotBlank(message = "User name should not be blank")
    private String name;

  
    @NotNull(message = "Invalid gender")
    @NotBlank(message = "Gender should not be blank")
    private String gender;

    
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age must not exceed 60")
    private int age;

    @NotNull(message = "Invalid city name")
    @NotBlank(message = "City name should not be blank")
    private String city;
    
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Number should not be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Number must be a 10-digit string")
    private String number;

    @NotNull(message = "Company should not be null")
    @NotBlank(message = "Company should not be blank")
    private String company;

    
    @NotNull(message = "Country should not be null")
    @Min(value = 0, message = "Salary must be a positive value")
    private long salary;

    @NotBlank(message = "Country should not be blank")
    @NotNull(message = "Country should not be null")
    private String country;
}
