package com.waes.assignment.util.dtos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.waes.assignment.model.DiffResult;
import com.waes.assignment.util.enums.DiffResultEnum;
/**
 * <p>
 * to give result of object and differences
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public class ResultDiffDto {

	private String result;
	private List<DetailDiffDto> differences;
	
	/**
     * <p>
     * The constructor for the class
     * </p>
     *
     * @param diffResult to contain the information about the diff between two items from the Domain
     */
    public ResultDiffDto(final DiffResult diffResult) {
        this.result = diffResult.getResultValue().getValue();
        if (!CollectionUtils.isEmpty(diffResult.getDifferencePoints())) {
            differences = diffResult.getDifferencePoints().stream().map(DetailDiffDto::new).collect(Collectors.toList());
        }
    }
    
    public ResultDiffDto(final DiffResultEnum givenEnum) {
    	this.result = givenEnum.getValue();
    	this.differences = null;
    }
    
    public String getResult() {
		return result;
	}
    
    public List<DetailDiffDto> getDifferences() {
		return differences;
	}
	
}
