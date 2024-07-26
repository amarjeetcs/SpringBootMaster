package com.springboot.master.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Entity
@Table(name = "Student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	//@JsonIgnore // it is used to ignore particular filed while fetching data from database
	private int id;
	private String name;
	//@JsonIgnore // it is used to ignore particular filed while fetching data from database
	private String gender;
	private int age;
	private String city;
	private String email;
	private String number;
	//@Transient
	private String company;
	private long salary;
	//@JsonIgnore
	private String country;

}