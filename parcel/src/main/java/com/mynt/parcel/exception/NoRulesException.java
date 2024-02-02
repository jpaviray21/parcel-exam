package com.mynt.parcel.exception;

public class NoRulesException extends RuntimeException{
	
	public NoRulesException() {
		super(String.format("No Rules Defined on the Database!"));
	}

}
