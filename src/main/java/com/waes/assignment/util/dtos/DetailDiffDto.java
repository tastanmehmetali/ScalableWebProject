package com.waes.assignment.util.dtos;

import org.apache.commons.lang3.tuple.Pair;

/**
 * <p>
 * details of Diff data
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public class DetailDiffDto {

	private int offset;
    private int length;
    
    /**
     * <p>
     * The constructor for the class
     * </p>
     *
     * @param diffDetail a Pair of Integers(Pair<Integer, Integer>) 
     * containing the offset in the left side and length in the right side
     */
    public DetailDiffDto(final Pair<Integer, Integer> diffDetail) {
        this.offset = (int) diffDetail.getLeft();
        this.length = (int) diffDetail.getRight();
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }
	
}
