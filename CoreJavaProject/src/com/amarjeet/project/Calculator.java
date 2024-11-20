package com.amarjeet.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;

public class Calculator extends JFrame implements ActionListener {
	// Declare components
	private JTextField display;
	private String currentInput = "";
	private double result = 0;
	private String lastOperator = "=";
	private double memory = 0; // Memory to store values (for M+, M-, MR, MC)

	// Constructor to set up the GUI
	public Calculator() {
		// Set up the JFrame
		setTitle("Enhanced Calculator");
		setSize(450, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Disable resizing of the window
		setResizable(false); // Prevent resizing

		// Display field (to show input and result)
		display = new JTextField();
		display.setFont(new Font("Arial", Font.PLAIN, 24));
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setEditable(false);
		add(display, BorderLayout.NORTH);

		// Panel for buttons
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 5, 10, 10));

		// Button labels
		String[] buttonLabels = { "7", "8", "9", "/", "sqrt", "4", "5", "6", "*", "^", "1", "2", "3", "-", "1/x", "C",
				"0", ".", "+", "=", "M+", "M-", "MR", "MC", "sin" };

		// Create and add buttons to the panel
		for (String label : buttonLabels) {
			JButton button = new JButton(label);
			button.setFont(new Font("Arial", Font.PLAIN, 24));
			button.addActionListener(this);
			panel.add(button);
		}

		add(panel, BorderLayout.CENTER);
	}

	// Handle button clicks
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		// If the command is a number or operator, append to the current input
		if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
			currentInput += command;
			display.setText(currentInput);
		}
		// If user presses 'C', clear the display
		else if (command.equals("C")) {
			currentInput = "";
			display.setText("");
		}
		// If user presses 'sqrt', calculate the square root
		else if (command.equals("sqrt")) {
			if (!currentInput.isEmpty()) {
				double value = Double.parseDouble(currentInput);
				result = Math.sqrt(value);
				display.setText(String.valueOf(result));
				currentInput = "";
			}
		}
		// If user presses '=', calculate the result
		else if (command.equals("=")) {
			if (!currentInput.isEmpty()) {
				calculate(Double.parseDouble(currentInput));
				display.setText(String.valueOf(result));
				currentInput = "";
			}
		}
		// If user presses an operator (+, -, *, /, etc.), perform the calculation
		else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
			if (!currentInput.isEmpty()) {
				calculate(Double.parseDouble(currentInput));
				currentInput = "";
			}
			lastOperator = command;
		}
		// If user presses an advanced function like square, reciprocal, or
		// exponentiation
		else {
			handleAdvancedFunctions(command);
		}
	}

	// Handle advanced operations like square, reciprocal, exponentiation, etc.
	private void handleAdvancedFunctions(String command) {
		try {
			if (command.equals("^")) { // Exponentiation (e.g., 2^3)
				if (!currentInput.isEmpty()) {
					double value = Double.parseDouble(currentInput);
					result = Math.pow(result, value);
					display.setText(String.valueOf(result));
					currentInput = "";
				}
			} else if (command.equals("1/x")) { // Reciprocal
				if (!currentInput.isEmpty()) {
					double value = Double.parseDouble(currentInput);
					if (value != 0) {
						result = 1 / value;
						display.setText(String.valueOf(result));
					} else {
						JOptionPane.showMessageDialog(this, "Cannot divide by zero!");
						result = 0;
					}
					currentInput = "";
				}
			} else if (command.equals("sin")) { // Sine function
				if (!currentInput.isEmpty()) {
					double value = Math.toRadians(Double.parseDouble(currentInput)); // Convert to radians
					result = Math.sin(value);
					display.setText(String.valueOf(result));
					currentInput = "";
				}
			} else if (command.equals("M+")) { // Add to memory
				if (!currentInput.isEmpty()) {
					memory += Double.parseDouble(currentInput);
					currentInput = "";
					display.setText("M+" + memory);
				}
			} else if (command.equals("M-")) { // Subtract from memory
				if (!currentInput.isEmpty()) {
					memory -= Double.parseDouble(currentInput);
					currentInput = "";
					display.setText("M-" + memory);
				}
			} else if (command.equals("MR")) { // Recall memory
				display.setText(String.valueOf(memory));
				currentInput = "";
			} else if (command.equals("MC")) { // Clear memory
				memory = 0;
				display.setText("Memory Cleared");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error: Invalid Input");
			currentInput = "";
		}
	}

	// Perform the calculation based on the last operator
	private void calculate(double number) {
		switch (lastOperator) {
		case "+":
			result += number;
			break;
		case "-":
			result -= number;
			break;
		case "*":
			result *= number;
			break;
		case "/":
			if (number != 0) {
				result /= number;
			} else {
				JOptionPane.showMessageDialog(this, "Cannot divide by zero!");
				result = 0;
			}
			break;
		case "=":
			result = number;
			break;
		}
	}

	// Main method to launch the calculator
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Create and show the enhanced calculator window
				Calculator calculator = new Calculator();
				calculator.setVisible(true);
			}
		});
	}
}
