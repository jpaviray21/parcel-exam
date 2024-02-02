package com.mynt.parcel.exception;

public class InvalidUnitException extends RuntimeException{

	public InvalidUnitException(String inputtedUnit) {
		super(String.format("%s is not a Valid unit", inputtedUnit));
	}
}
