package com.onprem.rk.number2word.services;

import com.onprem.rk.number2word.exceptions.NumberConversionException;
import com.onprem.rk.number2word.models.ConversionResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void convertNumberToWord_removes_whitespaces_and_commas_from_input() throws Exception {
        String input = "+0 0 2 , 9";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Twenty-Nine for input '+0 0 2 , 9'", conversionResponse.getOutput(), is("Twenty-Nine"));
    }

    @Test
    public void convertNumberToWord_handles_negative_numbers() throws Exception {
        String input = "-99";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Negative Ninety-Nine for input -99", conversionResponse.getOutput(), is("Negative Ninety-Nine"));
    }

    @Test
    public void convertNumberToWord_handles_negative_numbers_padded_zero() throws Exception {
        String input = "-000555000";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Negative Five Hundred and Fifty-Five Thousand for input -000555000", conversionResponse.getOutput(),
                is("Negative Five Hundred and Fifty-Five Thousand"));
    }

    @Test
    public void convertNumberToWord_handles_number_with_positive_sign() throws Exception {
        String input = "+99";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Ninety-Nine for input +99", conversionResponse.getOutput(), is("Ninety-Nine"));
    }

    @Test
    public void convertNumberToWord_returns_response_object() throws Exception {
        String input = "123";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should not be null", conversionResponse, notNullValue());
    }

    @Test
    public void convertNumberToWord_response_should_contain_input_string() throws Exception {
        String input = "123";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have input 123", conversionResponse.getInput(), is("123"));
    }

    @Test
    public void convertNumberToWord_response_should_contain_output_string() throws Exception {
        String input = "123";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output One Hundred and Twenty-Three", conversionResponse.getOutput(), is("One Hundred and Twenty-Three"));
    }

    @Test
    public void convertNumberToWord_response_should_contain_output_for_000() throws Exception {
        String input = "000";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output Zero for input 000", conversionResponse.getOutput(), is("Zero"));
    }

    @Test
    public void convertNumberToWord_response_should_contain_output_for_1() throws Exception {
        String input = "001";
        ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(input);
        assertThat("response should have output One for input 001", conversionResponse.getOutput(), is("One"));
    }

    @Test
    public void getWordsForTensAndOnes_should_convert_10() throws Exception {
        String conversionResponse = numberConversionService.getWordsForTensAndOnes(10);
        assertThat("response should have output Ten for input 10", conversionResponse, is("Ten"));
    }

    @Test
    public void getWordsForTensAndOnes_should_convert_19() throws Exception {
        String conversionResponse = numberConversionService.getWordsForTensAndOnes(19);
        assertThat("response should have output Ten for input 19", conversionResponse, is("Nineteen"));
    }

    @Test
    public void getWordsForTensAndOnes_should_convert_20() throws Exception {
        String conversionResponse = numberConversionService.getWordsForTensAndOnes(20);
        assertThat("response should have output Ten for input 20", conversionResponse, is("Twenty"));
    }

    @Test
    public void getWordsForTensAndOnes_should_convert_99() throws Exception {
        String conversionResponse = numberConversionService.getWordsForTensAndOnes(99);
        assertThat("response should have output Ten for input 99", conversionResponse, is("Ninety-Nine"));
    }

    @Test
    public void getWordsForTensAndOnes_should_throw_for_large_number() throws Exception {
        thrown.expect(NumberConversionException.class);
        thrown.expectMessage("Number 100 is too big for tens conversion");
        numberConversionService.getWordsForTensAndOnes(100);
    }

    @Test
    public void getWordForHundreds_should_convert_100() throws Exception {
        String conversionResponse = numberConversionService.getWordForHundreds(100);
        assertThat("response should have output Ten for input 99", conversionResponse, is("One Hundred"));
    }

    @Test
    public void getWordForHundreds_should_convert_999() throws Exception {
        String conversionResponse = numberConversionService.getWordForHundreds(999);
        assertThat("response should have output Nine Hundred for input 999", conversionResponse, is("Nine Hundred"));
    }

    @Test
    public void getWordForHundreds_should_throw_for_large_number() throws Exception {
        thrown.expect(NumberConversionException.class);
        thrown.expectMessage("Number 1000 is too big for hundred conversion");
        numberConversionService.getWordForHundreds(1000);
    }

    @Test
    public void convertThreeDigitNumberToWord_should_throw_for_invalid_input() throws Exception {
        thrown.expect(NumberConversionException.class);
        thrown.expectMessage("Invalid input: 10AB0");
        numberConversionService.convertNumberToWord("10AB0");
    }

    @Test
    public void convertThreeDigitNumberToWord_should_throw_for_positive_sign_in_middle_of_input() throws Exception {
        thrown.expect(NumberConversionException.class);
        thrown.expectMessage("Invalid input: 10+20");
        numberConversionService.convertNumberToWord("10+20");
    }

    @Test
    public void convertThreeDigitNumberToWord_should_convert_999() throws Exception {
        String conversionResponse = numberConversionService.convertThreeDigitNumberToWord(999);
        assertThat("response should have output Nine Hundred and Ninety-Nine for input 999",
                conversionResponse, is("Nine Hundred and Ninety-Nine"));
    }

    @Test
    public void convertThreeDigitNumberToWord_should_throw_for_large_number() throws Exception {
        thrown.expect(NumberConversionException.class);
        thrown.expectMessage("Number 1000 is too big for 3 digit number conversion");
        numberConversionService.convertThreeDigitNumberToWord(1000);
    }

    @Test
    public void getWordsForNumbers_should_return_words_in_groups() throws Exception {
        //[[789],[012]]
        List<List> input = Arrays.asList(Arrays.asList("789"), Arrays.asList("012"));
        List<List> output = numberConversionService.getWordsForNumbers(input);
        assertThat("should have 4 groups", output.size(), is(2));
        assertThat("first group should have 1 list element", output.get(0).size(), is(1));
        assertThat("first group list should contain 'Seven Hundred and Eighty-Nine'",
                output.get(0).get(0), is("Seven Hundred and Eighty-Nine"));
        assertThat("second group should have 1 list element", output.get(1).size(), is(1));
        assertThat("second group list should contain 'Twelve'", output.get(1).get(0), is("Twelve"));
    }

    @Test
    public void combineGroupWords_test_for_thousands() {
        // [[789],[012]]
        List<List> input = Arrays.asList(Arrays.asList("Seven Hundred and Eighty-Nine"), Arrays.asList("Twelve"));
        String output = numberConversionService.combineGroupWords(false, input);
        assertThat("[[789],[012]] should return Twelve Thousand, Seven Hundred and Eighty-Nine", output,
                is("Twelve Thousand, Seven Hundred and Eighty-Nine"));
    }

    @Test
    public void combineGroupWords_test_for_millions() {
        // [[456], [789],[012]]
        List<List> input = Arrays.asList(Arrays.asList("Four Hundred and Fifty-Six"),
                Arrays.asList("Seven Hundred and Eighty-Nine"), Arrays.asList("Twelve"));
        String output = numberConversionService.combineGroupWords(false, input);
        assertThat("[[456],[789],[012]] should return Twelve Million, Seven Hundred and Eighty-Nine Thousand, " +
                        "Four Hundred and Fifty-Six", output,
                is("Twelve Million, Seven Hundred and Eighty-Nine Thousand, Four Hundred and Fifty-Six"));
    }
}