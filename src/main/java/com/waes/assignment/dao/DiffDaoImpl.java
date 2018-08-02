package com.waes.assignment.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.waes.assignment.model.DiffEntity;

/**
 * <p>
 * to manage the storage process for the bussiness
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
@Repository
public class DiffDaoImpl implements DiffDao {

	private static final Map<String, DiffEntity> diffRepository = new HashMap<>();
	
	@Override
	public DiffEntity getDiffEntityById(final String givenId) {
		return diffRepository.get(givenId);
	}

	@Override
	public void saveOrUpdateDiffEntity(final DiffEntity diffEntity) {
		diffRepository.put(diffEntity.getId(), diffEntity);
	}

}
