package com.mynt.parcel.exception;

public class RuleAlreadyExistsException extends RuntimeException{
	
	public RuleAlreadyExistsException(String ruleName) {
		super(String.format("Rule name is already existing %s", ruleName));
	}

}
