package com.onprem.rk.number2word.services;

import com.onprem.rk.number2word.configs.NumberConfig;
import com.onprem.rk.number2word.models.ConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;

/**
 * Created by Rajib Khan on 04 Feb 2018
 * NumberConversionService converts number to words
 */

@Service
public class NumberConversionService {

    @Autowired
    NumberConfig numberConfig;

    /**
     * Converts number (integer) to words (string representation)
     * @param numberAsStr
     */
    public ConversionResponse convertNumberToWord(String numberAsStr) {
        // remove white spaces and comma from input
        numberAsStr = StringUtils.trimAllWhitespace(numberAsStr).replaceAll(",", "");
        //TODO input data validation
        ConversionResponse response = new ConversionResponse();
        response.setInput(numberAsStr);

        BigInteger inputNumber = new BigInteger(numberAsStr);
        BigInteger absNumber = inputNumber.abs();

        String output = "";

        if(absNumber.intValue() == 0) {
            output = numberConfig.getBasicNumberWords().get(0);
        } else if(absNumber.intValue() < 100) {
            output = convertTensAndOnes(absNumber.intValue());
        }

        if(inputNumber.signum() == -1) {
            output = numberConfig.getNegativeText() + " " + output;
        }
        response.setOutput(output);

        return response;
    }

    /**
     * Converts a number (smaller than 100) to corresponding words (tens and ones)
     * as defined in application properties file
     * @param number
     * @return
     */
    public String convertTensAndOnes(int number) {

        String groupText = "";
        int tens = number / 10;
        int ones = number % 10;

        // numbers below 20 have unique name / word
        if (tens >= 2)
        {
            groupText += numberConfig.getTensNumberWords().get(tens);
            if (ones != 0)
            {
                groupText += numberConfig.getJoinCharacterForTens() + numberConfig.getBasicNumberWords().get(ones);
            }
        }
        else if (number != 0)
            groupText += numberConfig.getBasicNumberWords().get(number);

        return groupText;
    }
}
