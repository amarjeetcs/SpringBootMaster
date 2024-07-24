package com.springboot.master.exception;

public class EmailAndNumberAlreadyExistException extends RuntimeException{
	public EmailAndNumberAlreadyExistException(String str)
	{
		super(str);
	}
}
