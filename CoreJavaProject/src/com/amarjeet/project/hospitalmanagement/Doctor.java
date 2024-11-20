package com.amarjeet.project.hospitalmanagement;

public class Doctor {
	private int doctorId;
	private String name;
	private String specialization;
	private String experience;

	public Doctor(int doctorId, String name, String specialization, String experience) {
		super();
		this.doctorId = doctorId;
		this.name = name;
		this.specialization = specialization;
		this.experience = experience;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "Doctor [doctorId=" + doctorId + ", name=" + name + ", specialization=" + specialization
				+ ", experience=" + experience + "]";
	}

}