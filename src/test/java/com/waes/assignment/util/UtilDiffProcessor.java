package com.waes.assignment.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import com.waes.assignment.model.DiffEntity;
import com.waes.assignment.util.enums.DiffSideTypeEnum;

/**
 * <p>
 * to define and represent the test scenario
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public class UtilDiffProcessor {
	
	public static final String ID_NOT_EXIST			= "ID_NOT_EXIST";
	public static final String ID_1 				= "ID_1";
	public static final String ID_2 				= "ID_2";
	public static final String ID_3 				= "ID_3";
	public static final String ID_123 				= "ID_123";
	public static final String ID_DUMMY_DATA 		= "ID_3213123123";
	public static final String SAMPLE_VALUE         = "Sample test for given value";
	public static final String SAMPLE_DIFF_VALUE	= "Sample tezt for given value";
	public static final String SAMPLE_DIFF_VALUE_ONE_MORE	= "Simple tezt for given value";
	public static final String GIVEN_LEFT_VALUE		= "{\"name\":\"givenLeftValue\"}";
	public static final String GIVEN_RIGHT_VALUE	= "{\"name\":\"givenRightValue\"}";
	public static final String GIVEN_JSON_SAMPLE_VALUE	= "{\"name\":\"givenValue\", \"details\":\"given details\", \"id\":\"ID_885522\"}";
	public static final String GIVEN_VALUE_MORE_COMPLEX	= "{\"name\":\"givenRightValue\", \"details\":\"It includes values complex more\"}";
	public static final String GIVEN_DIFF_VALUE_MORE_COMPLEX	= "{\"name\":\"givenValueRight\", \"details\":\"It includes more complex values\"}";
	public static final String OFFSET_IS_NOT_EXPECTED = "Offset is not the expected";
	public static final String LENGTH_IS_NOT_EXPECTED = "Length is not the expected";
	public static final String PREFIX_ENDPOINT_URL = "/v1/diff/";
	public static final String PREFIX_ENDPOINT_URL_WITH_ID = "/v1/diff/%s";
	public static final String PREFIX_ENDPOINT_URL_FOR_POST_REQ = PREFIX_ENDPOINT_URL_WITH_ID + "/%s";

	
	public static DiffEntity getDiffEntityWithOnlyGivenId() {
		return generateDiffEntity(ID_1, null, null);
	}
	
	public static DiffEntity getDiffEntityWithGivenIdAndLeftValue() {
		return generateDiffEntity(ID_1, GIVEN_LEFT_VALUE, null);
	}
	
	public static DiffEntity getDiffEntityWithGivenIdAndRightValue() {
		return generateDiffEntity(ID_1, null, GIVEN_RIGHT_VALUE);
	}
	
	public static DiffEntity getDiffEntityWithSameValueForLeftAndRight() {
		return generateDiffEntity(ID_1, SAMPLE_VALUE, SAMPLE_VALUE);
	}
	
	public static DiffEntity getDiffEntityWithDiffSizeValueForLeftAndRight() {
		return generateDiffEntity(ID_1, GIVEN_LEFT_VALUE, GIVEN_RIGHT_VALUE);
	}
	
	public static DiffEntity getDiffEntityWithSameSizeDiffValueForLeftAndRight() {
		return generateDiffEntity(ID_1, SAMPLE_VALUE, SAMPLE_DIFF_VALUE);
	}
	
	public static DiffEntity getDiffEntityWithMoreComplexValueForLeftAndRight() {
		return generateDiffEntity(ID_1, GIVEN_VALUE_MORE_COMPLEX, GIVEN_DIFF_VALUE_MORE_COMPLEX);
	}
	
	public static final byte[] getSampleContentData() {
		return Base64Utils.encode(GIVEN_JSON_SAMPLE_VALUE.getBytes());
	}
	
	public static String generateLeftEndpointForGivenId(final String givenId) {
		return String.format(PREFIX_ENDPOINT_URL_FOR_POST_REQ, givenId, DiffSideTypeEnum.LEFT_SIDE.getValue());
	}
	
	public static String generateRightEndpointForGivenId(final String givenId) {
		return String.format(PREFIX_ENDPOINT_URL_FOR_POST_REQ, givenId, DiffSideTypeEnum.RIGHT_SIDE.getValue());
	}
	
	private static DiffEntity generateDiffEntity(final String givenId, final String givenLeftValue, final String givenRightValue) {
		DiffEntity diffEntity = new DiffEntity(givenId);
		if(StringUtils.isNotEmpty(givenLeftValue)) {
			diffEntity.updateValueForLeftSide(givenLeftValue);
		}
		
		if(StringUtils.isNotEmpty(givenRightValue)) {
			diffEntity.updateValueForRightSide(givenRightValue);
		}
		
		return diffEntity;
	}
	
}
