package com.onprem.rk.number2word.controllers;

import com.onprem.rk.number2word.exceptions.NumberConversionException;
import com.onprem.rk.number2word.models.ConversionResponse;
import com.onprem.rk.number2word.services.NumberConversionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by Rajib Khan on 04 Feb 2018
 * NumberConversionControllerTest tests success and error responses for various scenarios
 */

@RunWith(SpringRunner.class)
@WebMvcTest(NumberConversionController.class)
public class NumberConversionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NumberConversionService numberConversionService;

    @Test
    public void given_valid_input_returns_json() throws Exception {
        String input = "123";
        given(numberConversionService.convertNumberToWord("123"))
                .willReturn(new ConversionResponse("123","One Hundred and Twenty-Three"));
        mvc.perform(get("/numberToString/" + input)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.input", is(input)))
                .andExpect(jsonPath("$.output", is("One Hundred and Twenty-Three")));
    }

    @Test
    public void given_invalid_input_returns_error() throws Exception {
        String input = "123abn";
        given(numberConversionService.convertNumberToWord("123abn"))
                .willThrow(new NumberConversionException("invalid number"));
        mvc.perform(get("/numberToString/" + input)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("invalid number"));
    }

}