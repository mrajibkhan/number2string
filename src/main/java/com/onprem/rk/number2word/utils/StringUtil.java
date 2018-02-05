package com.onprem.rk.number2word.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rajib Khan on 05 Feb 2018
 * StringUtil contains utility methods used for split user inputs
 */
public class StringUtil {

    /**
     * Splits the input string (number) into groups from right to left. Each group can contain certain number of
     * digits (@param itemsInGroup) and maximum number of groups are limited to the value of @param totalGroups.
     * If there are remaining items after the distribution then remaining items are grouped in the last group
     * using recursion.
     * @param numberStr string representation of the number
     * @param totalGroups total number of groups to represent the number string
     * @param itemsInGroup number of items in a group, e.g. there is 3 items in a 3 digit number as string
     * @return
     */
    public static List<List> splitNumberStringIntoGroups(String numberStr, int totalGroups, int itemsInGroup) {
        List<List> digitGroups = new ArrayList<>();
        int endIndex = numberStr.length();

        String currentGroupStr = "";
        for(int i = 0; i < totalGroups && endIndex > 0; i++) {
            int startIndex = (endIndex >=itemsInGroup) ? endIndex - itemsInGroup : 0;

            if((endIndex <= itemsInGroup) || (i == totalGroups -1)) {
                currentGroupStr = numberStr.substring(0,endIndex);
                endIndex = 0;
            } else {
                currentGroupStr = numberStr.substring(startIndex, endIndex);
                endIndex -= itemsInGroup;
            }

            if(currentGroupStr.length() > itemsInGroup) {
                digitGroups.add(splitNumberStringIntoGroups(currentGroupStr, totalGroups, itemsInGroup));
            } else {
                digitGroups.add(Arrays.asList(currentGroupStr));
            }
        }

        return digitGroups;
    }
}
