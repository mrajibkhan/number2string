package com.onprem.rk.number2word.configs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumberConfigTest {

    @Autowired
    NumberConfig numberConfig;

    @Test
    public void getBasicNumberWords_should_return_20_words() {
        List<String> basicNumberWords = numberConfig.getBasicNumberWords();
        assertThat("should have 20 elements", basicNumberWords.size(), is(20));
    }

    @Test
    public void getBasicNumberWords_should_return_zero_as_first_element() {
        List<String> basicNumberWords = numberConfig.getBasicNumberWords();
        assertThat("should return zero as first element", basicNumberWords.get(0), is("Zero"));
    }

    @Test
    public void getBasicNumberWords_should_return_nineteen_as_last_element() {
        List<String> basicNumberWords = numberConfig.getBasicNumberWords();
        assertThat("should return nineteen as last element", basicNumberWords.get(19), is("Nineteen"));
    }

    @Test
    public void getTensNumberWords_should_return_10_words() {
        List<String> tensNumberWords = numberConfig.getTensNumberWords();
        assertThat("should have 10 elements", tensNumberWords.size(), is(10));
    }

    @Test
    public void getTensNumberWords_should_return_first_element_as_blank() {
        List<String> tensNumberWords = numberConfig.getTensNumberWords();
        assertThat("should have blank first element", tensNumberWords.get(0), is(""));
    }

    @Test
    public void getTensNumberWords_should_return_second_element_as_blank() {
        List<String> tensNumberWords = numberConfig.getTensNumberWords();
        assertThat("should have blank first element", tensNumberWords.get(1), is(""));
    }

    @Test
    public void getTensNumberWords_should_return_third_element_as_twenty() {
        List<String> tensNumberWords = numberConfig.getTensNumberWords();
        assertThat("should have blank first element", tensNumberWords.get(2), is("Twenty"));
    }

    @Test
    public void getTensNumberWords_should_return_last_element_as_ninety() {
        List<String> tensNumberWords = numberConfig.getTensNumberWords();
        assertThat("should have blank first element", tensNumberWords.get(9), is("Ninety"));
    }

    @Test
    public void getJoinCharacterForTens_should_return_hyphen() {
        assertThat("should return hyphen(-) as join char for tens", numberConfig.getJoinCharacterForTens(), is("-"));
    }

    @Test
    public void getNegativeText_should_return_negative() {
        assertThat("should return Negative as the text to represent negative numbers",
                numberConfig.getNegativeText(), is("Negative"));
    }

}