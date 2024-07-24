package com.springboot.master.exception;

public class NumberAlreadyExistsException extends RuntimeException{
	public NumberAlreadyExistsException(String str)
	{
		super(str);
	}
}
