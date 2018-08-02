package com.waes.assignment.service;

import com.waes.assignment.model.DiffResult;

/**
 * <p>
 * The interface describes that all the DiffService functionalities
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public interface DiffService {

	/**
	 * <p>
     * to evaluate the difference between the left and right values of the given ID
     * </p>
	 * 
	 * 
	 * @param givenId
	 * @return DiffResult that the result of the method gets 
	 * the differences between the left and right values
	 * 
	 */
	DiffResult getDifference(final String givenId);

	/**
	 * <p>
	 * to storing the right value for a given ID within the application
	 * </p>
	 * 
	 * @param givenId that used identification of the object
	 * @param decodeForGivenValue String formatted JSON data
	 */
	void saveRightSide(final String givenId, final String decodeForGivenValue);

	/**
	 * <p>
	 * to storing the left value for a given ID within the application
	 * </p>
	 * 
	 * @param givenId that used identification of the object
	 * @param decodeForGivenValue String formatted JSON data
	 */
	void saveLeftSide(final String givenId, final String decodeForGivenValue);
	
}
