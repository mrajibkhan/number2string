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
    public void response_should_contain_input_string() {
        String input = "123";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have input 123", conversionResponse.getInput(), is("123"));
    }

    @Test
    public void response_should_contain_output_for_000() {
        String input = "000";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Zero for input 000", conversionResponse.getOutput(), is("Zero"));
    }

    @Test
    public void response_should_contain_output_for_1() {
        String input = "001";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output One for input 001", conversionResponse.getOutput(), is("One"));
    }

    @Test
    public void response_should_contain_output_for_10() {
        String input = "10";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Ten for input 10", conversionResponse.getOutput(), is("Ten"));
    }

    @Test
    public void response_should_contain_output_for_19() {
        String input = "19";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Ten for input 19", conversionResponse.getOutput(), is("Nineteen"));
    }

    @Test
    public void response_should_contain_output_for_20() {
        String input = "20";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Ten for input 20", conversionResponse.getOutput(), is("Twenty"));
    }

    @Test
    public void response_should_contain_output_for_99() {
        String input = "99";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Ten for input 99", conversionResponse.getOutput(), is("Ninety-Nine"));
    }

    @Test
    public void it_removes_whitespaces_and_commas_from_input() {
        String input = "0 0 2 , 9";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Ten for input 99", conversionResponse.getOutput(), is("Twenty-Nine"));
    }

    @Test
    public void it_handles_negative_numbers() {
        String input = "-99";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Ten for input 99", conversionResponse.getOutput(), is("Negative Ninety-Nine"));
    }

}