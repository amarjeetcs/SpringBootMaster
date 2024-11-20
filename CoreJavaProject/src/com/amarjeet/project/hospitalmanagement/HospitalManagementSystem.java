package com.amarjeet.project.hospitalmanagement;

import java.util.*;

public class HospitalManagementSystem {

	private static Map<Integer, Patient> patients = new HashMap<>();
	private static Map<Integer, Doctor> doctors = new HashMap<>();
	private static Map<Integer, List<Patient>> appointments = new HashMap<>();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Hospital Management System");
			System.out.println("1. Add Patient");
			System.out.println("2. Add Doctor");
			System.out.println("3. View Patients");
			System.out.println("4. View Doctors");
			System.out.println("5. Schedule Appointment");
			System.out.println("6. View Appointments");
			System.out.println("7. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // consume newline

			switch (choice) {
			case 1:
				addPatient(scanner);
				break;
			case 2:
				addDoctor(scanner);
				break;
			case 3:
				viewPatients();
				break;
			case 4:
				viewDoctors();
				break;
			case 5:
				scheduleAppointment(scanner);
				break;
			case 6:
				viewAppointments();
				break;
			case 7:
				System.out.println("Exiting system.");
				return;
			default:
				System.out.println("Invalid choice! Try again.");
			}
		}
	}

	// Add a new patient
	private static void addPatient(Scanner scanner) {
		System.out.print("Enter Patient ID: ");
		int patientId = scanner.nextInt();
		scanner.nextLine(); // consume newline
		System.out.print("Enter Patient Name: ");
		String name = scanner.nextLine();
		System.out.print("Enter Patient Age: ");
		int age = scanner.nextInt();
		scanner.nextLine(); // consume newline
		System.out.print("Enter Disease: ");
		String disease = scanner.nextLine();

		System.out.println("Choose Doctor:");
		for (Doctor doctor : doctors.values()) {
			System.out.println(doctor.getDoctorId() + ". " + doctor.getName());
		}
		System.out.print("Enter Doctor ID: ");
		int doctorId = scanner.nextInt();
		Doctor assignedDoctor = doctors.get(doctorId);

		Patient patient = new Patient(patientId, name, age, disease, assignedDoctor);
		patients.put(patientId, patient);
		System.out.println("Patient added successfully.");
	}

	// Add a new doctor
	private static void addDoctor(Scanner scanner) {
		System.out.print("Enter Doctor ID: ");
		int doctorId = scanner.nextInt();
		scanner.nextLine(); // consume newline
		System.out.print("Enter Doctor Name: ");
		String name = scanner.nextLine();
		System.out.print("Enter Specialization: ");
		String specialization = scanner.nextLine();
		
		System.out.print("Enter Experience: ");
		String experience = scanner.nextLine();

		Doctor doctor = new Doctor(doctorId, name, specialization, experience);
		doctors.put(doctorId, doctor);
		System.out.println("Doctor added successfully.");
	}

	// View all patients
	private static void viewPatients() {
		if (patients.isEmpty()) {
			System.out.println("No patients available.");
		} else {
			for (Patient patient : patients.values()) {
				System.out.println(patient);
			}
		}
	}

	// View all patients
		private static void viewDoctors() {
			if (doctors.isEmpty()) {
				System.out.println("No Doctor available.");
			} else {
				for (Doctor doctor : doctors.values()) {
					System.out.println(doctor);
				}
			}
		}
	// Schedule an appointment
	private static void scheduleAppointment(Scanner scanner) {
		System.out.print("Enter Patient ID: ");
		int patientId = scanner.nextInt();
		scanner.nextLine(); // consume newline

		Patient patient = patients.get(patientId);
		if (patient == null) {
			System.out.println("Patient not found!");
			return;
		}

		System.out.print("Enter Appointment Date (dd/mm/yyyy): ");
		String appointmentDate = scanner.nextLine();

		appointments.computeIfAbsent(patientId, k -> new ArrayList<>()).add(patient);
		System.out.println("Appointment scheduled for " + patient.getName() + " with Dr. "
				+ patient.getAssignedDoctor().getName() + " on " + appointmentDate);
	}

	// View all appointments
	private static void viewAppointments() {
		if (appointments.isEmpty()) {
			System.out.println("No appointments available.");
		} else {
			for (Map.Entry<Integer, List<Patient>> entry : appointments.entrySet()) {
				System.out.println("Patient ID: " + entry.getKey());
				for (Patient patient : entry.getValue()) {
					System.out.println(patient);
				}
			}
		}
	}
}
