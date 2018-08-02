package com.waes.assignment.domain;

import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithDiffSizeValueForLeftAndRight;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithGivenIdAndLeftValue;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithGivenIdAndRightValue;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithOnlyGivenId;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithSameSizeDiffValueForLeftAndRight;
import static com.waes.assignment.util.UtilDiffProcessor.getDiffEntityWithSameValueForLeftAndRight;

import org.junit.Assert;
import org.junit.Test;

import com.waes.assignment.model.DiffEntity;
import com.waes.assignment.util.UtilDiffProcessor;

/**
 * <p>
 * to check the domain object
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public class DiffEntityTest {

	@Test
	public void testDiffEntityCanBeSetGivenId() {
		DiffEntity diffEntity = getDiffEntityWithOnlyGivenId();
		Assert.assertEquals(UtilDiffProcessor.ID_1, diffEntity.getId());
		Assert.assertNull(diffEntity.getLeft());
		Assert.assertNull(diffEntity.getRight());
	}

	@Test
	public void testCanBeSetGivenIdAndLeftValue() {
		DiffEntity diffEntity = getDiffEntityWithGivenIdAndLeftValue();
		Assert.assertEquals(UtilDiffProcessor.ID_1, diffEntity.getId());
		Assert.assertEquals(UtilDiffProcessor.GIVEN_LEFT_VALUE, diffEntity.getLeft());
		Assert.assertNull(diffEntity.getRight());
	}

	@Test
	public void testCanBeSetGivenIdAndRightValue() {
		DiffEntity diffEntity = getDiffEntityWithGivenIdAndRightValue();
		Assert.assertEquals(UtilDiffProcessor.ID_1, diffEntity.getId());
		Assert.assertEquals(UtilDiffProcessor.GIVEN_RIGHT_VALUE, diffEntity.getRight());
		Assert.assertNull(diffEntity.getLeft());
	}
	
	@Test
	public void testGivenSameValueForRightAndLeftSide() {
		DiffEntity diffEntity = getDiffEntityWithSameValueForLeftAndRight();
		Assert.assertEquals(diffEntity.getLeft(), diffEntity.getRight());
		Assert.assertNotNull(diffEntity.getLeft());
		Assert.assertNotNull(diffEntity.getRight());
	}
	
	@Test
	public void testGivenValueDifferentSizeForLeftAndRightSide(){
		DiffEntity diffEntity = getDiffEntityWithDiffSizeValueForLeftAndRight();
		Assert.assertNotEquals(diffEntity.getLeft(), diffEntity.getRight());
		Assert.assertNotNull(diffEntity.getLeft());
		Assert.assertNotNull(diffEntity.getRight());
	}
	
	@Test
	public void testGivenValueSameSizeDiffValueForLeftAndRightSide(){
		DiffEntity diffEntity = getDiffEntityWithSameSizeDiffValueForLeftAndRight();
		Assert.assertNotEquals(diffEntity.getLeft(), diffEntity.getRight());
		Assert.assertNotNull(diffEntity.getLeft());
		Assert.assertNotNull(diffEntity.getRight());
	}
	
}
