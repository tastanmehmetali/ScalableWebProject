package com.waes.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waes.assignment.exception.NotFoundException;
import com.waes.assignment.service.DiffService;
import com.waes.assignment.util.dtos.ResultDiffDto;
import com.waes.assignment.util.enums.DiffResultEnum;

/**
 * <p>
 * The Class, based on REST, is the part that manages the right and left data and diff
 * for received req.
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
@RestController
@RequestMapping("/v1/diff/{ID}")
public class DiffRestController {

	@Autowired
	private DiffService diffService;

	/**
	 * This method is GET Rest Endpoint the endpoint to get the differences
	 * between the left and right values
	 * 
	 *
	 * @param givenId
	 *            that the ID used to identify the object
	 * @return the ResponseEntity with a ResultDiffDto is the evaluted result
	 */
	@GetMapping()
	public ResponseEntity<ResultDiffDto> getDifference(@PathVariable("ID") final String givenId) {
		try {
			ResultDiffDto diffResultDTO = new ResultDiffDto(diffService.getDifference(givenId));
			return new ResponseEntity<>(diffResultDTO, HttpStatus.OK);
		} catch (NotFoundException entityNotFoundException) {
			return new ResponseEntity<>(new ResultDiffDto(DiffResultEnum.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * This method is POST Rest Endpoint that to store the right value for the given Id
	 *  
	 * 
	 * @param givenId that identify the object
	 * @param givenObject that (base64) encoded binary data
	 * @return ResponseEntity (TRUE) whenever everything is perfect
	 */
	@PostMapping(path = "/right")
	public ResponseEntity saveRightSide(@PathVariable("ID") final String givenId, @RequestBody final byte[] givenObject) {
		diffService.saveRightSide(givenId, decodeForGivenValue(givenObject));
		return ResponseEntity.ok(Boolean.TRUE);
	}
	
	/**
	 * This method is POST Rest Endpoint the endpoint to store the left value for the given Id
	 *  
	 * 
	 * @param givenId that identify the object
	 * @param givenObject that (base64) encoded binary data
	 * @return ResponseEntity (TRUE) whenever everything is perfect
	 */
	@PostMapping(path = "/left")
	public ResponseEntity saveLeftSide(@PathVariable("ID") final String givenId, @RequestBody final byte[] givenObject) {
		diffService.saveLeftSide(givenId, decodeForGivenValue(givenObject));
		return ResponseEntity.ok(Boolean.TRUE);
	}

    /**
     * to decode for given encoded value
     * 
     * @param givenEncodedValue
     * @return
     */
    private String decodeForGivenValue(final byte[] givenEncodedValue) {
		return new String(Base64Utils.decode(givenEncodedValue));
    }
}
