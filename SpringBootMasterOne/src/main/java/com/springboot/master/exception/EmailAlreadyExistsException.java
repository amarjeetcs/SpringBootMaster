package com.springboot.master.exception;

public class EmailAlreadyExistsException extends RuntimeException{
	public EmailAlreadyExistsException(String str)
	{
		super(str);
	}
}
