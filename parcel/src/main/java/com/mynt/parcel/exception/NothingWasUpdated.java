package com.mynt.parcel.exception;

public class NothingWasUpdated extends RuntimeException {
	
	public NothingWasUpdated(Long ruleId) {
		super(String.format("No Updates applied for id: %s", ruleId));
	}

}
