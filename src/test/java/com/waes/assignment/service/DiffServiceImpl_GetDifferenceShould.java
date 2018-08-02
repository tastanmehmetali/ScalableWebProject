package com.waes.assignment.service;

import static com.waes.assignment.util.UtilDiffProcessor.ID_1;
import static com.waes.assignment.util.UtilDiffProcessor.ID_DUMMY_DATA;
import static com.waes.assignment.util.UtilDiffProcessor.OFFSET_IS_NOT_EXPECTED;
import static com.waes.assignment.util.UtilDiffProcessor.LENGTH_IS_NOT_EXPECTED;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithDiffSizeValueForLeftAndRight;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithGivenIdAndLeftValue;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithGivenIdAndRightValue;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithMoreComplexValueForLeftAndRight;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithSameSizeDiffValueForLeftAndRight;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithSameValueForLeftAndRight;
import static org.mockito.Mockito.when;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import com.waes.assignment.dao.DiffDao;
import com.waes.assignment.exception.NotFoundException;
import com.waes.assignment.model.DiffEntity;
import com.waes.assignment.model.DiffResult;
import com.waes.assignment.util.enums.DiffResultEnum;

/**
 * <p>
 * This class is responsible for testing the behavior of the getDiff method in
 * the DiffServiceImpl class
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public class DiffServiceImpl_GetDifferenceShould {

	private DiffEntity diffEntity;

	@Mock
	private DiffDao diffDao;

	@InjectMocks
	private DiffServiceImpl diffService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = InvalidParameterException.class)
	public void testFailGetDifferenceGivenIdIsEmpty() {
		diffService.getDifference(StringUtils.EMPTY);
	}

	@Test(expected = InvalidParameterException.class)
	public void failGetDifferenceGivenIdIsNull() {
		diffService.getDifference(null);
	}

	@Test(expected = NotFoundException.class)
	public void failDiffEntityByGivenIdNotExist() {
		when(diffDao.getDiffEntityById(ID_DUMMY_DATA)).thenReturn(null);
		diffService.getDifference(ID_1);
	}

	@Test
	public void testNotIncludeLeftValueFromDiffEntity() {
		diffEntity = getDiffEntityWithGivenIdAndRightValue();
		when(diffDao.getDiffEntityById(ID_1)).thenReturn(diffEntity);
		DiffResult diffResult = diffService.getDifference(ID_1);
		Assert.assertEquals(DiffResultEnum.MISSING_LEFT_VALUE, diffResult.getResultValue());
		Assert.assertTrue("DifferencePoints should be empty",
				CollectionUtils.isEmpty(diffResult.getDifferencePoints()));
	}

	@Test
	public void testNotIncludeRightValueFromDiffEntity() {
		diffEntity = getDiffEntityWithGivenIdAndLeftValue();
		when(diffDao.getDiffEntityById(ID_1)).thenReturn(diffEntity);
		DiffResult diffResult = diffService.getDifference(ID_1);
		Assert.assertEquals(DiffResultEnum.MISSING_RIGHT_VALUE, diffResult.getResultValue());
		Assert.assertTrue("DifferencePoints should be empty",
				CollectionUtils.isEmpty(diffResult.getDifferencePoints()));
	}

	@Test
	public void testSameValueforRightAndLeftFromDiffEntity() {
		diffEntity = getDiffEntityWithSameValueForLeftAndRight();
		when(diffDao.getDiffEntityById(ID_1)).thenReturn(diffEntity);
		DiffResult diffResult = diffService.getDifference(ID_1);
		Assert.assertEquals(DiffResultEnum.EQUALS_GIVEN_VALUES, diffResult.getResultValue());
		Assert.assertTrue("DifferencePoints should be empty",
				CollectionUtils.isEmpty(diffResult.getDifferencePoints()));
	}

	@Test
	public void testDiffSizeValueforRightAndLeftFromDiffEntity() {
		diffEntity = getDiffEntityWithDiffSizeValueForLeftAndRight();
		when(diffDao.getDiffEntityById(ID_1)).thenReturn(diffEntity);
		DiffResult diffResult = diffService.getDifference(ID_1);
		Assert.assertEquals(DiffResultEnum.DIFFERENT_SIZE_FOR_GIVEN_VALUES, diffResult.getResultValue());
		Assert.assertTrue("DifferencePoints should be empty",
				CollectionUtils.isEmpty(diffResult.getDifferencePoints()));
	}

	@Test
	public void testSameSizeDiffValueforRightAndLeftFromDiffEntity() {
		diffEntity = getDiffEntityWithSameSizeDiffValueForLeftAndRight();
		when(diffDao.getDiffEntityById(ID_1)).thenReturn(diffEntity);
		DiffResult diffResult = diffService.getDifference(ID_1);
		Assert.assertEquals(DiffResultEnum.GIVEN_VALUES_HAVE_NOT_SAME_CONTENT, diffResult.getResultValue());
		Assert.assertFalse("DifferencePoints should not be empty",
				CollectionUtils.isEmpty(diffResult.getDifferencePoints()));
		List<Pair<Integer, Integer>> actualDiffList = new ArrayList<Pair<Integer,Integer>>();
		actualDiffList.add(Pair.of(9,1));
		checkDiffList(diffResult.getDifferencePoints(), actualDiffList);
	}

	@Test
	public void testMoreComplexValueForRightAndLeftFromDiffEntity() {
		diffEntity = getDiffEntityWithMoreComplexValueForLeftAndRight();
		when(diffDao.getDiffEntityById(ID_1)).thenReturn(diffEntity);
		DiffResult diffResult = diffService.getDifference(ID_1);
		
		List<Pair<Integer, Integer>> actualDiffList = new ArrayList<Pair<Integer,Integer>>();
		actualDiffList.add(Pair.of(14,10));
		actualDiffList.add(Pair.of(50,19));
		
		Assert.assertEquals(DiffResultEnum.GIVEN_VALUES_HAVE_NOT_SAME_CONTENT, diffResult.getResultValue());
		Assert.assertFalse("DifferencePoints should not be empty",
				CollectionUtils.isEmpty(diffResult.getDifferencePoints()));
		checkDiffList(diffResult.getDifferencePoints(), actualDiffList);
		
	}
	
	private void checkDiffList(final List<Pair<Integer, Integer>> expectedList, final List<Pair<Integer, Integer>> givenRealList) {
        Assert.assertEquals("different size for given expectedList and realList", expectedList.size(), givenRealList.size());
        for (int index = 0; index < expectedList.size(); index++) {
        	Assert.assertEquals(OFFSET_IS_NOT_EXPECTED, expectedList.get(index).getLeft(), givenRealList.get(index).getLeft());
        	Assert.assertEquals(LENGTH_IS_NOT_EXPECTED, expectedList.get(index).getRight(), givenRealList.get(index).getRight());
        }
    }


}