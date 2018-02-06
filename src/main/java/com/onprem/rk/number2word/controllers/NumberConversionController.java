package com.onprem.rk.number2word.controllers;

import com.onprem.rk.number2word.exceptions.NumberConversionException;
import com.onprem.rk.number2word.models.ConversionResponse;
import com.onprem.rk.number2word.services.NumberConversionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Rajib Khan on 04 Feb 2018
 * NumberConversionController consists of REST endpoint for number to string conversion
 */

@Controller
@Slf4j
public class NumberConversionController {

    @Autowired
    NumberConversionService numberConversionService;

    @RequestMapping(value="/numberToString/{number}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ConversionResponse> convertNumberToString(@PathVariable("number") String numberStr) {
        log.info("Request- input string: {}", numberStr );
        ResponseEntity response;
        try {
            ConversionResponse conversionResponse = numberConversionService.convertNumberToWord(numberStr);
            response = new ResponseEntity<>(conversionResponse, HttpStatus.OK);
        } catch (NumberConversionException ex) {
            log.error(ex.getMessage());
            response = ResponseEntity.badRequest().body(ex.getMessage());
        }
        log.info("Response- output " + response);
        return response;
    }
}




