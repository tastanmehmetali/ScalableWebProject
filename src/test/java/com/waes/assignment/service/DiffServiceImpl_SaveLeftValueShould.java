package com.waes.assignment.service;

import static com.waes.assignment.util.UtilDiffProcessor.GIVEN_LEFT_VALUE;
import static com.waes.assignment.util.UtilDiffProcessor.ID_1;
import static com.waes.assignment.util.UtilDiffProcessor.ID_NOT_EXIST;
import static com.waes.assignment.util.UtilDiffProcessor.SAMPLE_DIFF_VALUE;
import static com.waes.assignment.util.UtilDiffProcessor.SAMPLE_VALUE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.waes.assignment.dao.DiffDao;
import com.waes.assignment.model.DiffEntity;

/**
 * <p>
 * to manage the test cases of the application for saving value to left side
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public class DiffServiceImpl_SaveLeftValueShould {

	@Mock
	private DiffDao diffDao;

	@InjectMocks
	private DiffServiceImpl diffService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = InvalidParameterException.class)
	public void testFailSaveLeftValueByGivenIdIsEmpty() {
		diffService.saveLeftSide(StringUtils.EMPTY, GIVEN_LEFT_VALUE);
	}

	@Test(expected = InvalidParameterException.class)
	public void testFailSaveLeftValueByGivenIdIsNull() {
		diffService.saveLeftSide(null, GIVEN_LEFT_VALUE);
	}

	@Test(expected = InvalidParameterException.class)
	public void testFailSaveLeftValueIsEmptyByGivenId() {
		diffService.saveLeftSide(ID_1, StringUtils.EMPTY);
	}

	@Test(expected = InvalidParameterException.class)
	public void testFailSaveLeftValueIsNullByGivenId() {
		diffService.saveLeftSide(ID_1, null);
	}

	@Test
	public void testUpdateTheValueForExistDiffData() {
		DiffEntity oldDiffEntity = new DiffEntity(ID_1);
        oldDiffEntity.updateValueForRightSide(SAMPLE_VALUE);
        oldDiffEntity.updateValueForLeftSide(SAMPLE_VALUE);

        DiffEntity newDiffEntity = new DiffEntity(ID_1);
        newDiffEntity.updateValueForRightSide(SAMPLE_VALUE);
        newDiffEntity.updateValueForLeftSide(SAMPLE_DIFF_VALUE);
        
        when(diffDao.getDiffEntityById(ID_1)).thenReturn(oldDiffEntity);
        diffService.saveLeftSide(ID_1, SAMPLE_DIFF_VALUE);
        verify(diffDao).saveOrUpdateDiffEntity(newDiffEntity);
        Assert.assertEquals("The left Value is not be updated.", SAMPLE_DIFF_VALUE, newDiffEntity.getLeft());
    }

	@Test
	public void testCreateNewDiffForNotExistData() {
		DiffEntity tempDiffEntity = new DiffEntity(ID_NOT_EXIST);
		tempDiffEntity.updateValueForLeftSide(SAMPLE_VALUE);
		
		when(diffDao.getDiffEntityById(ID_NOT_EXIST)).thenReturn(null);
		diffService.saveLeftSide(ID_NOT_EXIST, SAMPLE_VALUE);
		verify(diffDao).saveOrUpdateDiffEntity(tempDiffEntity);
        Assert.assertEquals("Newly created entity not include any left value.", SAMPLE_VALUE, tempDiffEntity.getLeft());
    }
	
	@Test
	public void testCheckedRightValueForNewlyCreatedData() {
		DiffEntity tempDiffEntity = new DiffEntity(ID_NOT_EXIST);
		tempDiffEntity.updateValueForLeftSide(SAMPLE_VALUE);
		Assert.assertNull(tempDiffEntity.getRight());
		
		when(diffDao.getDiffEntityById(ID_NOT_EXIST)).thenReturn(null);
		diffService.saveLeftSide(ID_NOT_EXIST, SAMPLE_VALUE);
		verify(diffDao).saveOrUpdateDiffEntity(tempDiffEntity);
		Assert.assertEquals("Left value is null for newly created entity.", SAMPLE_VALUE, tempDiffEntity.getLeft());
    }

}
