package com.mynt.parcel.exception;

public class CannotDeleteWeightLimit extends RuntimeException {

	public CannotDeleteWeightLimit(Long ruleId) {
		super(String.format("Cannot Delete rule as this is the weight max limit", ruleId));
	}
}
