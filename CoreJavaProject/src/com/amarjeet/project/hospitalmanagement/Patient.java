package com.amarjeet.project.hospitalmanagement;

public class Patient {
	private int patientId;
	private String name;
	private int age;
	private String disease;
	private Doctor assignedDoctor;

	public Patient(int patientId, String name, int age, String disease, Doctor assignedDoctor) {
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.disease = disease;
		this.assignedDoctor = assignedDoctor;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public Doctor getAssignedDoctor() {
		return assignedDoctor;
	}

	public void setAssignedDoctor(Doctor assignedDoctor) {
		this.assignedDoctor = assignedDoctor;
	}

	@Override
	public String toString() {
		return "Patient ID: " + patientId + ", Name: " + name + ", Age: " + age + ", Disease: " + disease + ", Doctor: "
				+ assignedDoctor.getName();
	}
}
