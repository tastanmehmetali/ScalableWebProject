package com.waes.assignment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <p>
 * to represent the entity of the Diff operation
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public class DiffEntity {

	private String id;
	private String left;
	private String right;

	/**
	 * <p>
     * The constructor for the class
     * </p>
	 * 
	 * @param id that to be compared the objects.
	 */
	public DiffEntity(final String givenId) {
		this.id = givenId;
		this.left = null;
		this.right = null;
	}
	
	public String getId() {
		return id;
	}

	public String getLeft() {
		return left;
	}

	public void updateValueForLeftSide(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void updateValueForRightSide(String right) {
		this.right = right;
	}

    @Override
    public boolean equals(final Object givenObject) {
        return EqualsBuilder.reflectionEquals(this, givenObject);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
