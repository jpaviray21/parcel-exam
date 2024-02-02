package com.mynt.parcel.exception;

public class RuleWeightLimitExceededException extends RuntimeException{
	
	public RuleWeightLimitExceededException(String enteredLimit, String maxLimit) {
		super(String.format("Weight %s KG exceeded the maximum %s KG", enteredLimit, maxLimit));
	}

}
