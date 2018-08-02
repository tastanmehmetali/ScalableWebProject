package com.waes.assignment.util.enums;

/**
 * <p>
 * to give values for the application side
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public enum DiffSideTypeEnum {

	LEFT_SIDE("left"), RIGHT_SIDE("right");
	
	private final String value;
	
	DiffSideTypeEnum(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

}
