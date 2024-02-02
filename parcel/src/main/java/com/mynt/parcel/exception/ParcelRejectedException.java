package com.mynt.parcel.exception;

public class ParcelRejectedException extends RuntimeException {
	
	public ParcelRejectedException(double weight, double limit) {
		super(String.format("Parcel is Rejected due to Limit Restriction %s KG is over %s KG limit", weight, limit));
	}

}
