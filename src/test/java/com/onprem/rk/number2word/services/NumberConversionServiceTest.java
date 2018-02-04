package com.onprem.rk.number2word.services;

import com.onprem.rk.number2word.models.ConversionResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Rajib Khan on 04 Feb 2018
 * NumberConversionServiceTest tests conversion for different input scenarios
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumberConversionServiceTest {

    @Autowired
    private NumberConversionService numberConversionService;

    @Test
    public void it_returns_response_object() {
        String input = "123";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should not be null", conversionResponse, notNullValue());
    }

    @Test
    public void response_contains_input_string() {
        String input = "123";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have input 123", conversionResponse.getInput(), is("123"));
    }

}