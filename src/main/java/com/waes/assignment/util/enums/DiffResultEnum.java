package com.waes.assignment.util.enums;

/**
 * <p>
 * to hold the types of response messages for the Diff operations
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public enum DiffResultEnum {
    NOT_FOUND("Given value is not exist"),
    MISSING_LEFT_VALUE("Left value is missing"),
    MISSING_RIGHT_VALUE("Right value is missing"),
    EQUALS_GIVEN_VALUES("Given Values are equal."),
    DIFFERENT_SIZE_FOR_GIVEN_VALUES("Given values have different sizes"),
    GIVEN_VALUES_HAVE_NOT_SAME_CONTENT("Values have different content");

    private final String value;

    DiffResultEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
