package com.waes.assignment.service;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waes.assignment.dao.DiffDao;
import com.waes.assignment.exception.NotFoundException;
import com.waes.assignment.model.DiffEntity;
import com.waes.assignment.model.DiffResult;
import com.waes.assignment.util.enums.DiffResultEnum;

/**
 * <p>
 * managing bussiness logic for the application
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
@Service
public class DiffServiceImpl implements DiffService {

	public static final String REQUIRED_PARAMETER_CAN_NOT_BE_EMPTY = "%s, required parameter, cannot be empty";
	public static final String NOT_FOUND_ENTITY_FOR_GIVEN_ID = "Not found any entity for given id, %s";

	@Autowired
	DiffDao diffDao;

	/**
	 * <p>
	 * to return result of stored data by givenId and if it has differences (offset and length)
	 * </p>
	 * 
	 * @return DiffResult
	 */
	@Override
	public DiffResult getDifference(final String givenId) {
		DiffEntity diffEntity = getDiffData(givenId);
		return checkDiffForAnyAttributes(diffEntity);
	}

	/**
	 * <p>
	 * to check given id is valid or not
	 * </p>
	 * 
	 * @param givenId that given id for the stored data
	 * @param givenValue that stored value
	 */
	private void validateGivenObject(final String givenId, final String givenValue) {
		if (StringUtils.isEmpty(givenId)) {
			throw new InvalidParameterException(String.format(REQUIRED_PARAMETER_CAN_NOT_BE_EMPTY, givenValue));
		}
	}

	/**
	 * <p>
	 * to find the domain object by given id
	 * </p>
	 * 
	 * @param givenId
	 * @return DiffEntity that is entity of found data or thrown exception
	 */
	private DiffEntity getDiffData(final String givenId) {
		validateGivenObject(givenId, "givenId");
		DiffEntity diffEntity = diffDao.getDiffEntityById(givenId);
		if (diffEntity == null)
			throw new NotFoundException(String.format(NOT_FOUND_ENTITY_FOR_GIVEN_ID, givenId));
		return diffEntity;
	}

	/**
	 * <p>
	 * to check diff attributes 
	 * (missing left value, missing right value, equals, diff size, diff content)
	 * </p>
	 * 
	 * @param diffEntity that found object
	 * @return DiffResult return any result of the business logic
	 */
	private DiffResult checkDiffForAnyAttributes(final DiffEntity diffEntity) {
		if (StringUtils.isEmpty(diffEntity.getLeft())) {
			return new DiffResult(DiffResultEnum.MISSING_LEFT_VALUE);
		}

		if (StringUtils.isEmpty(diffEntity.getRight())) {
			return new DiffResult(DiffResultEnum.MISSING_RIGHT_VALUE);
		}

		if (diffEntity.getLeft().equals(diffEntity.getRight())) {
			return new DiffResult(DiffResultEnum.EQUALS_GIVEN_VALUES);
		}

		if (diffEntity.getLeft().length() != diffEntity.getRight().length()) {
			return new DiffResult(DiffResultEnum.DIFFERENT_SIZE_FOR_GIVEN_VALUES);
		}

		return comparedAllDifferences(diffEntity.getLeft(), diffEntity.getRight());
	}
	
	/**
     * Process the differences inside two strings of equal size, 
     * returning the offset and length of each difference
     *
     * @param givenLeftValue  The first string
     * @param givenRightValue The second string
     * @return DiffResult has A list of DiffDetailDTO containing the offset and length of each difference
     */
	private DiffResult comparedAllDifferences(final String givenLeftValue, final String givenRightValue) {
		DiffResult result = new DiffResult(DiffResultEnum.GIVEN_VALUES_HAVE_NOT_SAME_CONTENT);
		
        boolean checkedFlag = false;
        int diffLength = 0;
        int diffOffset = 0;

        for (int offset = 0; offset < givenLeftValue.length(); offset++) {
            if (givenLeftValue.charAt(offset) != givenRightValue.charAt(offset)) {
                if (checkedFlag) {
                    diffLength++;
                } else {
                	checkedFlag = true;
                    diffOffset = offset;
                    diffLength++;
                }
            } else {
                if (checkedFlag) {
                	checkedFlag = false;
                    result.addDifferenceDetail(diffOffset, diffLength);
                    diffLength = 0;
                    diffOffset = 0;
                }
            }
        }

        if (checkedFlag) {
            result.addDifferenceDetail(diffOffset, diffLength);
        }
        
        return result;
	}
	
	/**
	 * <p>
	 * to save or update value to right side  for found object by id
	 * </p>
	 * 
	 * @param givenId that given id to find stored object
	 * @param givenData that to store found object by id
	 */
	@Override
	public void saveRightSide(final String givenId, final String givenData) {
		DiffEntity diffEntity = updatedValueGenerator(givenId, givenData);
		diffEntity.updateValueForRightSide(givenData);
		diffDao.saveOrUpdateDiffEntity(diffEntity);
	}
	
	/**
	 * <p>
	 * to save or update value to left side  for found object by id
	 * </p>
	 * 
	 * @param givenId that given id to find stored object
	 * @param givenData that to store found object by id
	 */
	@Override
	public void saveLeftSide(final String givenId, final String givenData) {
		DiffEntity diffEntity = updatedValueGenerator(givenId, givenData);
		diffEntity.updateValueForLeftSide(givenData);
		diffDao.saveOrUpdateDiffEntity(diffEntity);
	}

	/**
	 * <p>
	 * to update given value for found object by givenId
	 * </p>
	 * 
	 * @param givenId
	 * @param givenData
	 * @return
	 */
	private DiffEntity updatedValueGenerator(final String givenId, final String givenData) {
		validateGivenObject(givenId, "givenId");
		validateGivenObject(givenData, "jsonObj");
		DiffEntity diffEntity = diffDao.getDiffEntityById(givenId);
		if (diffEntity == null) {
			diffEntity = new DiffEntity(givenId);
		}

		return diffEntity;
	}
}
