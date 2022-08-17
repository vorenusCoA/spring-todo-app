package com.example.todo.exception;

public class IllegalActivityDeletionAttempt extends Exception {

	private static final long serialVersionUID = 1L;

	public IllegalActivityDeletionAttempt(String message) {
		super(message);
	}
	
}
