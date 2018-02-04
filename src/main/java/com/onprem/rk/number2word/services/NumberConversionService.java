package com.onprem.rk.number2word.services;

import com.onprem.rk.number2word.models.ConversionResponse;
import org.springframework.stereotype.Service;

/**
 * Created by Rajib Khan on 04 Feb 2018
 * NumberConversionService converts number to words
 */

@Service
public class NumberConversionService {

    /**
     * Converts number (integer) to words (string representation)
     * @param numberAsStr
     */
    public ConversionResponse convertNumberToWord(String numberAsStr) {
        ConversionResponse response = new ConversionResponse();
        response.setInput(numberAsStr);
        response.setOutput("");

        return response;
    }
}
