package com.waes.assignment.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

import com.waes.assignment.util.enums.DiffResultEnum;

/**
 * <p>
 * to represent the result of the Diff operation between two objects
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public class DiffResult {

    DiffResultEnum resultValue;
    List<Pair<Integer, Integer>> differencePoints;

    /**
     * <p>
     * The constructor for the class
     * </p>
     *
     * @param resultValue the Enum for the Diff operation
     */
    public DiffResult(final DiffResultEnum resultValue) {
        this.resultValue = resultValue;
    }

    /**
     * <p>
     * managing for adding new references about the differences between two objects
     * </p>
     *
     * @param offset the position where the difference starts
     * @param size   the size of the difference, representing how many characters are different starting from the offset
     */
    public void addDifferenceDetail(final int offset, final int size) {
        if (differencePoints == null) {
            differencePoints = new ArrayList<>();
        }
        differencePoints.add(Pair.of(offset, size));
    }

    public List<Pair<Integer, Integer>> getDifferencePoints() {
        return differencePoints;
    }

    public DiffResultEnum getResultValue() {
        return resultValue;
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
        return ToStringBuilder.reflectionToString(this);
    }
}
