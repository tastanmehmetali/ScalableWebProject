package com.waes.assignment.dao;

import com.waes.assignment.model.DiffEntity;

/**
 * <p>
 * The class that has repository methods about get data by id and save given entity
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public interface DiffDao {

	/**
	 * to get DiffEntity by given Id
	 * 
	 * @param givenId that entered id
	 * @return DiffEntity
	 */
	DiffEntity getDiffEntityById(final String givenId);

	
	/**
	 * to store the given object.
	 * 
	 * @param diffEntity that whenever left and/or right values set
	 */
	void saveOrUpdateDiffEntity(final DiffEntity diffEntity);
	
}
