package com.onprem.rk.number2word.utils;

import org.junit.Test;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class StringUtilTest {

    @Test
    public void splitNumberStringIntoGroups_should_return_4_groups() {
        // expected: [[012], [789], [456], [123]]
        List<List> groups = StringUtil.splitNumberStringIntoGroups("123456789012", 4, 3);
        assertThat("should have 4 groups", groups.size(), is(4));
        assertThat("first group should have 1 list element", groups.get(0).size(), is(1));
        assertThat("first group list should contain '123'", groups.get(0).get(0), is("012"));
        assertThat("second group should have 1 list element", groups.get(1).size(), is(1));
        assertThat("second group list should contain '789'", groups.get(1).get(0), is("789"));
        assertThat("third group should have 1 list element", groups.get(2).size(), is(1));
        assertThat("third group list should contain '456'", groups.get(2).get(0), is("456"));
        assertThat("fourth group should have 1 list element", groups.get(3).size(), is(1));
        assertThat("fourth group list should contain '123'", groups.get(3).get(0), is("123"));
    }

    @Test
    public void splitNumberStringIntoGroups_should_return_2_groups_in_4th_group() {
        // expected: [[345], [012], [789], [[456], [123]]]
        List<List> groups = StringUtil.splitNumberStringIntoGroups("123456789012345", 4, 3);
        System.out.println(groups);
        assertThat("should have 4 groups", groups.size(), is(4));
        assertThat("should have 2 sub-groups in 4th group", groups.get(3).size(), is(2));
        assertThat("first sub-group should contain 456", ((List)groups.get(3).get(0)).get(0), is("456"));
        assertThat("second sub-group should contain 123", ((List)groups.get(3).get(1)).get(0), is("123"));
    }

    @Test
    public void isStringValidNumber_should_return_true_for_12345() {
        assertThat("should return true for 12345", StringUtil.isStringValidNumber("12345"), is(true));
    }

    @Test
    public void isStringValidNumber_should_return_false_for_12abc45() {
        assertThat("should return false for 12abc45", StringUtil.isStringValidNumber("12abc45"), is(false));
    }

    @Test
    public void isZero_should_return_false_for_12abc45() {
        assertThat("should return false for 12abc45", StringUtil.isZero("12abc45"), is(false));
    }

    @Test
    public void isZero_should_return_false_for_000555000() {
        assertThat("should return false for 12abc45", StringUtil.isZero("000555000"), is(false));
    }

    @Test
    public void isZero_should_return_true_for_0() {
        assertThat("should return true for 0", StringUtil.isZero("0"), is(true));
    }

    @Test
    public void isZero_should_return_true_for_000() {
        assertThat("should return true for 000", StringUtil.isZero("000"), is(true));
    }
}