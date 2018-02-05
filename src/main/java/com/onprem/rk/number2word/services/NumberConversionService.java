package com.onprem.rk.number2word.services;

import com.onprem.rk.number2word.configs.NumberConfig;
import com.onprem.rk.number2word.exceptions.NumberConversionException;
import com.onprem.rk.number2word.models.ConversionResponse;
import com.onprem.rk.number2word.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        //TODO input data validation
        ConversionResponse response = new ConversionResponse();
        response.setInput(numberAsStr);

        // remove white spaces and comma from input
        numberAsStr = StringUtils.trimAllWhitespace(numberAsStr).replaceAll(",", "");
        boolean isNegative = numberAsStr.startsWith("-");
        if(isNegative) {
            numberAsStr = numberAsStr.replaceFirst("-", "");
        }

        List<List> numberGroups = StringUtil.splitNumberStringIntoGroups(numberAsStr,
                numberConfig.getLargeScaleWords().size(), 3);
        System.out.println("Groups: " + numberGroups);
        List<List> wordGroups = getWordsForNumbers(numberGroups);
        System.out.println("Words: " + wordGroups);

        int firstGroupNumber = Integer.valueOf((String)numberGroups.get(0).get(0));
        boolean appendAndToJoin = firstGroupNumber > 0 && firstGroupNumber < 100;
        String combinedOutput = combineGroupWords(appendAndToJoin, wordGroups);

        String output = "";

        if(isNegative) {
            output = numberConfig.getNegativeText() + " " + combinedOutput;
        }

        response.setOutput(combinedOutput);

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

    /**
     * Iterates through the list of numbers and returns list of words.
     * @param groups
     * @return
     * @throws NumberConversionException
     */
    public List<List> getWordsForNumbers(List<List> groups) throws NumberConversionException {
        List<List> groupAsWords = new ArrayList<>();
        for(List currentGroup : groups) {
            if(currentGroup.size() == 1) {
                groupAsWords.add(Arrays.asList(convertThreeDigitNumberToWord(Integer.valueOf((String)currentGroup.get(0)))));
            } else if(groups.size() > 1) {
                groupAsWords.add(getWordsForNumbers(currentGroup));
            }
        }

        return groupAsWords;
    }

    /**
     * Combines the group words. Uses recursion if the largest scale group contains
     * sub-groups. For each group words the scale and punctuation is appended.
     * @param appendAnd
     * @param groupText
     * @return
     */

    public String combineGroupWords(boolean appendAnd, List<List> groupText) {
        // flatten the largest group
        List<List> largestScaleGroup = groupText.get(groupText.size() -1 );
        if(largestScaleGroup != null && largestScaleGroup.size() > 1) {
            groupText.set(groupText.size() -1, new ArrayList(Arrays.asList(combineGroupWords(appendAnd, largestScaleGroup))));
        }

        // Recombine the three-digit groups
        String combined = (String)groupText.get(0).get(0);

        // Process the remaining groups in turn, smallest to largest
        for (int i = 1; (i < numberConfig.getLargeScaleWords().size() && i < groupText.size()); i++)
        {
            // Only add non-zero items
            if (!((String)groupText.get(i).get(0)).isEmpty())
            {
                // Build the string to add as a prefix
                String prefix = (String)groupText.get(i).get(0) + " " + numberConfig.getLargeScaleWords().get(i);

                if (combined.length() != 0)
                {
                    prefix += appendAnd ? " and " : ", ";
                }

                appendAnd = false;

                // Add the three-digit group to the combined string
                combined = prefix + combined;
            }
        }

        return combined;
    }


}
