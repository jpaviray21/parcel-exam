package com.mynt.parcel.exception;

public class CannotDeleteVolumeLimit extends RuntimeException {

	public CannotDeleteVolumeLimit(Long ruleId) {
		super(String.format("Cannot Delete rule as this is the volume max limit", ruleId));
	}
}
