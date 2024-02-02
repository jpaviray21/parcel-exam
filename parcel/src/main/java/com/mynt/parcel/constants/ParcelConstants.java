package com.mynt.parcel.constants;

public enum ParcelConstants {
	VOLUME_CODE("V"),
	WEIGHT_CODE("W"),
	
	SUCCESSFUL_RULE_SAVE_CD("PAC-200"),
	SUCCESSFUL_RULE_SAVE_MSG("You have successfully added a new Rule!"),
	SUCCESSFUL_RULE_UPDATE_CD("PAU-200"),
	SUCCESSFUL_RULE_UPDATE_MSG("You have successfully updated a Rule!"),
	SUCCESSFUL_RULE_DELETE_CD("PAD-200"),
	SUCCESSFUL_RULE_DELETE_MSG("You have successfully deleted a Rule!"),
	;
	
	String code;
	String value;
	
	private ParcelConstants(String value) {
		this.value = value;
		
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
		

}
