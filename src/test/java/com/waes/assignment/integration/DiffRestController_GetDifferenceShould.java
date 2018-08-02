package com.waes.assignment.integration;

import static com.waes.assignment.util.UtilDiffProcessor.GIVEN_JSON_SAMPLE_VALUE;
import static com.waes.assignment.util.UtilDiffProcessor.GIVEN_LEFT_VALUE;
import static com.waes.assignment.util.UtilDiffProcessor.GIVEN_RIGHT_VALUE;
import static com.waes.assignment.util.UtilDiffProcessor.ID_1;
import static com.waes.assignment.util.UtilDiffProcessor.ID_2;
import static com.waes.assignment.util.UtilDiffProcessor.ID_3;
import static com.waes.assignment.util.UtilDiffProcessor.ID_NOT_EXIST;
import static com.waes.assignment.util.UtilDiffProcessor.PREFIX_ENDPOINT_URL;
import static com.waes.assignment.util.UtilDiffProcessor.PREFIX_ENDPOINT_URL_FOR_POST_REQ;
import static com.waes.assignment.util.UtilDiffProcessor.PREFIX_ENDPOINT_URL_WITH_ID;
import static com.waes.assignment.util.UtilDiffProcessor.SAMPLE_DIFF_VALUE;
import static com.waes.assignment.util.UtilDiffProcessor.SAMPLE_DIFF_VALUE_ONE_MORE;
import static com.waes.assignment.util.UtilDiffProcessor.SAMPLE_VALUE;
import static com.waes.assignment.util.UtilDiffProcessor.GIVEN_VALUE_MORE_COMPLEX;
import static com.waes.assignment.util.UtilDiffProcessor.GIVEN_DIFF_VALUE_MORE_COMPLEX;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import com.waes.assignment.DiffApplication;
import com.waes.assignment.util.enums.DiffResultEnum;
import com.waes.assignment.util.enums.DiffSideTypeEnum;

/**
 * <p>
 * to call the object for using endpoints
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
@SpringBootTest(classes = DiffApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DiffRestController_GetDifferenceShould {

	@Autowired
	MockMvc mockDiffMvc;

	@Test
	public void testReturnNotFoundForGivenIdIsEmpty() throws Exception {
		mockDiffMvc.perform(get(String.format(PREFIX_ENDPOINT_URL_WITH_ID, StringUtils.EMPTY)))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testReturnNotFoundForGivenIdIsNull() throws Exception {
		mockDiffMvc.perform(get(PREFIX_ENDPOINT_URL + null)).andExpect(status().isNotFound());
	}

	@Test
	public void testReturnNotFoundForGivenIdIsNotExist() throws Exception {
		mockDiffMvc.perform(get(String.format(PREFIX_ENDPOINT_URL_WITH_ID, ID_NOT_EXIST)))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testReturnResultForMissingLeftValue() throws Exception {
		generateDataForGivenObject(ID_1, GIVEN_JSON_SAMPLE_VALUE, DiffSideTypeEnum.RIGHT_SIDE);
		mockDiffMvc.perform(get(String.format(PREFIX_ENDPOINT_URL_WITH_ID, ID_1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is(DiffResultEnum.MISSING_LEFT_VALUE.getValue())))
				.andExpect(jsonPath("$.differences", is(nullValue())));
	}

	@Test
	public void testReturnResultForMissingRightValue() throws Exception {
		generateDataForGivenObject(ID_2, GIVEN_JSON_SAMPLE_VALUE, DiffSideTypeEnum.LEFT_SIDE);
		mockDiffMvc.perform(get(String.format(PREFIX_ENDPOINT_URL_WITH_ID, ID_2))).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is(DiffResultEnum.MISSING_RIGHT_VALUE.getValue())))
				.andExpect(jsonPath("$.differences", is(nullValue())));
	}

	@Test
	public void testReturnResultForGivenSameValueLeftAndRight() throws Exception {
		generateDataForGivenObject(ID_3, GIVEN_JSON_SAMPLE_VALUE, DiffSideTypeEnum.LEFT_SIDE);
		generateDataForGivenObject(ID_3, GIVEN_JSON_SAMPLE_VALUE, DiffSideTypeEnum.RIGHT_SIDE);

		mockDiffMvc.perform(get(String.format(PREFIX_ENDPOINT_URL_WITH_ID, ID_3))).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is(DiffResultEnum.EQUALS_GIVEN_VALUES.getValue())))
				.andExpect(jsonPath("$.differences", is(nullValue())));
	}

	@Test
	public void testReturnResultForGivenDiffSizeValueForLeftAndRight() throws Exception {
		generateDataForGivenObject(ID_3, GIVEN_LEFT_VALUE, DiffSideTypeEnum.LEFT_SIDE);
		generateDataForGivenObject(ID_3, GIVEN_RIGHT_VALUE, DiffSideTypeEnum.RIGHT_SIDE);

		mockDiffMvc.perform(get(String.format(PREFIX_ENDPOINT_URL_WITH_ID, ID_3))).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is(DiffResultEnum.DIFFERENT_SIZE_FOR_GIVEN_VALUES.getValue())))
				.andExpect(jsonPath("$.differences", is(nullValue())));
	}

	@Test
	public void testReturnResultForGivenSameSizeDiffValue() throws Exception {
		generateDataForGivenObject(ID_3, SAMPLE_VALUE, DiffSideTypeEnum.LEFT_SIDE);
		generateDataForGivenObject(ID_3, SAMPLE_DIFF_VALUE, DiffSideTypeEnum.RIGHT_SIDE);

		mockDiffMvc.perform(get(String.format(PREFIX_ENDPOINT_URL_WITH_ID, ID_3))).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is(DiffResultEnum.GIVEN_VALUES_HAVE_NOT_SAME_CONTENT.getValue())))
				.andExpect(jsonPath("$.differences", iterableWithSize(1)))
				.andExpect(jsonPath("$.differences[0].offset", is(9)))
				.andExpect(jsonPath("$.differences[0].length", is(1)));
	}

	@Test
	public void testReturnResultForGivenSameSizeDiffValueOneMore() throws Exception {
		generateDataForGivenObject(ID_3, SAMPLE_VALUE, DiffSideTypeEnum.LEFT_SIDE);
		generateDataForGivenObject(ID_3, SAMPLE_DIFF_VALUE_ONE_MORE, DiffSideTypeEnum.RIGHT_SIDE);

		mockDiffMvc.perform(get(String.format(PREFIX_ENDPOINT_URL_WITH_ID, ID_3))).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is(DiffResultEnum.GIVEN_VALUES_HAVE_NOT_SAME_CONTENT.getValue())))
				.andExpect(jsonPath("$.differences", iterableWithSize(2)))
				.andExpect(jsonPath("$.differences[0].offset", is(1)))
				.andExpect(jsonPath("$.differences[0].length", is(1)))
				.andExpect(jsonPath("$.differences[1].offset", is(9)))
				.andExpect(jsonPath("$.differences[1].length", is(1)));
	}

	@Test
	public void testReturnResultForGivenSameSizeDiffValueMoreComplex() throws Exception {
		generateDataForGivenObject(ID_3, GIVEN_VALUE_MORE_COMPLEX, DiffSideTypeEnum.LEFT_SIDE);
		generateDataForGivenObject(ID_3, GIVEN_DIFF_VALUE_MORE_COMPLEX, DiffSideTypeEnum.RIGHT_SIDE);

		mockDiffMvc.perform(get(String.format(PREFIX_ENDPOINT_URL_WITH_ID, ID_3))).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is(DiffResultEnum.GIVEN_VALUES_HAVE_NOT_SAME_CONTENT.getValue())))
				.andExpect(jsonPath("$.differences", iterableWithSize(2)))
				.andExpect(jsonPath("$.differences[0].offset", is(14)))
				.andExpect(jsonPath("$.differences[0].length", is(10)))
				.andExpect(jsonPath("$.differences[1].offset", is(50)))
				.andExpect(jsonPath("$.differences[1].length", is(19)));
	}

	private void generateDataForGivenObject(final String givenId, final String givenData,
			final DiffSideTypeEnum givenSide) throws Exception {
		mockDiffMvc.perform(post(String.format(PREFIX_ENDPOINT_URL_FOR_POST_REQ, givenId, givenSide.getValue()))
				.content(Base64Utils.encode(givenData.getBytes()))).andExpect(status().isOk());
	}

}
