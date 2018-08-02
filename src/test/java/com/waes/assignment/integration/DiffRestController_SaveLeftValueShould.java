package com.waes.assignment.integration;

import static com.waes.assignment.util.UtilDiffProcessor.GIVEN_JSON_SAMPLE_VALUE;
import static com.waes.assignment.util.UtilDiffProcessor.ID_1;
import static com.waes.assignment.util.UtilDiffProcessor.generateLeftEndpointForGivenId;
import static com.waes.assignment.util.UtilDiffProcessor.getSampleContentData;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.waes.assignment.DiffApplication;

/**
 * <p>
 * to call the object or save/update data for using left value endpoints
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
@SpringBootTest(classes = DiffApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DiffRestController_SaveLeftValueShould {

	@Autowired
	MockMvc mockDiffMvc;

	@Test
	public void testCreatedDiffDataForGivenIdIsEmpty() throws Exception {
		mockDiffMvc.perform(post(generateLeftEndpointForGivenId(StringUtils.EMPTY)).content(getSampleContentData()))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testCreatedDiffDataForGivenIdIsNull() throws Exception {
		mockDiffMvc.perform(post(generateLeftEndpointForGivenId(null)).content(getSampleContentData()))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreatedDiffDataForGivenValidValues() throws Exception {
		mockDiffMvc.perform(post(generateLeftEndpointForGivenId(ID_1)).content(getSampleContentData()))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreatedDiffDataWhenNotIncludeContent() throws Exception {
		mockDiffMvc.perform(post(generateLeftEndpointForGivenId(ID_1))).andExpect(status().is4xxClientError());
	}

	@Test
	public void testCreatedDiffDataWhenIncludeContentIsEmpty() throws Exception {
		mockDiffMvc.perform(post(generateLeftEndpointForGivenId(ID_1)).content(StringUtils.EMPTY))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testCreatedDiffDataWhenIncludeContentIsNull() throws Exception {
		mockDiffMvc.perform(post(generateLeftEndpointForGivenId(ID_1)).content(new byte[0]))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testClientErrorWhenContentNotValidBase64() throws Exception {
		mockDiffMvc.perform(post(generateLeftEndpointForGivenId(ID_1)).content(GIVEN_JSON_SAMPLE_VALUE))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testClientErrorWhenContentByteNotValidBase64() throws Exception {
		mockDiffMvc.perform(post(generateLeftEndpointForGivenId(ID_1)).content(GIVEN_JSON_SAMPLE_VALUE.getBytes()))
				.andExpect(status().is4xxClientError());
	}

}
