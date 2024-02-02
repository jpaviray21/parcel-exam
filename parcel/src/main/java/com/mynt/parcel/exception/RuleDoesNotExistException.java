package com.mynt.parcel.exception;

public class RuleDoesNotExistException extends RuntimeException{
	
	public RuleDoesNotExistException(Long rule) {
		super(String.format("Entered ruleid does not exist: %s", rule));
	}

}
