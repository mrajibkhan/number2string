package com.onprem.rk.number2word.services;

import com.onprem.rk.number2word.configs.NumberConfig;
import com.onprem.rk.number2word.exceptions.NumberConversionException;
import com.onprem.rk.number2word.models.ConversionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;

/**
 * Created by Rajib Khan on 04 Feb 2018
 * NumberConversionService converts number to words
 */

@Service
@Slf4j
public class NumberConversionService {

    @Autowired
    NumberConfig numberConfig;

    /**
     * Converts number (integer) to words (string representation)
     * @param numberAsStr
     */
    public ConversionResponse convertNumberToWord(String numberAsStr) throws NumberConversionException {
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
        } else {
            output = convertThreeDigitNumberToWord(absNumber.intValue());
        }

        if(inputNumber.signum() == -1) {
            output = numberConfig.getNegativeText() + " " + output;
        }
        response.setOutput(output);

        return response;
    }

    /**
     * Converts a number (smaller than 100) to corresponding words (tens and ones)
     * as defined in application properties file. Uses absolute value i.e. doesn't add any
     * word for negative number.
     * @param number
     * @return
     */
    public String getWordsForTensAndOnes(int number) throws NumberConversionException {

        String groupText = "";
        int tens = number / 10;
        int ones = number % 10;

        if(tens > 9) {
            throw new NumberConversionException(String.format("Number %d is too big for tens conversion", number));
        }

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

    /**
     * Gets words for the hundred portion corresponding to a number (between 100 and 900) e.g. converts 100 or 199 to "One Hundred".
     * Uses absolute value i.e. doesn't add any word for negative number.
     * @param number
     * @return
     * @throws NumberConversionException
     */
    public String getWordForHundreds(int number) throws NumberConversionException {
        StringBuilder groupText = new StringBuilder("");
        int hundreds = number / 100;
        if(hundreds > 9) {
            throw new NumberConversionException(String.format("Number %d is too big for hundred conversion", number));
        } else if (hundreds != 0)
        {
            groupText.append(numberConfig.getBasicNumberWords().get(hundreds)).append(" ").append(numberConfig.getHundred());
        }

        return groupText.toString();
    }

    /**
     * Converts a three digit number to words. Uses absolute value i.e. doesn't add any
     * word for negative number.
     * @param number
     * @return
     * @throws NumberConversionException
     */
    public String convertThreeDigitNumberToWord (int number) throws NumberConversionException {
        StringBuilder groupText = new StringBuilder();

        if(number >= 1000) {
            throw new NumberConversionException(String.format("Number %d is too big for 3 digit number conversion", number));
        }

        int tens = number % 100;

        groupText.append(getWordForHundreds(number));

        if (tens != 0)
        {
            if(groupText.length() > 0) {
                groupText.append(" ").append(numberConfig.getJoinByText3Digit()).append(" ");
            }
            groupText.append(getWordsForTensAndOnes(tens));
        }

        return groupText.toString();
    }


}
