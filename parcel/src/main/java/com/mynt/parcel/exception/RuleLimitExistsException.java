package com.mynt.parcel.exception;

public class RuleLimitExistsException extends RuntimeException {
	
	public RuleLimitExistsException(String enteredLimit) {
		super(String.format("Entered limit is already existing %s", enteredLimit));
	}
}
