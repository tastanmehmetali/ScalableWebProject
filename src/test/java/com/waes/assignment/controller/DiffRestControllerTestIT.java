package com.waes.assignment.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DiffRestControllerTestIT {

    private static String newNumber = "/v1/number";
    private static String results = "/v1/result";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnTheCorrectSumAndAverage() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        HttpEntity<String> httpEntity = new HttpEntity<>("1", headers);

        int totalNumbers = 10000;

        IntStream.range(0, totalNumbers)
                .parallel()
                .forEach(i -> {
                    ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity(
                            newNumber,
                            httpEntity,
                            Void.class);
                    then(responseEntity.getStatusCode()).isEqualByComparingTo(OK);
                });

        ResponseEntity<NumberDetailResponseContract> responseContractResponseEntity = testRestTemplate.getForEntity(
                results,
                NumberDetailResponseContract.class,
                null,
                null);

        NumberDetailResponseContract expectedNumberDetailResponseContract = new NumberDetailResponseContract();
        expectedNumberDetailResponseContract.setSum((long) totalNumbers);
        expectedNumberDetailResponseContract.setAvg((totalNumbers / (double) totalNumbers));

        then(responseContractResponseEntity.getStatusCode()).isEqualByComparingTo(OK);
        then(responseContractResponseEntity.getBody()).isEqualTo(expectedNumberDetailResponseContract);
    }
}