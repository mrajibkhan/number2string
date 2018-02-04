package com.onprem.rk.number2word.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by Rajib Khan on 04 Feb 2018
 * NumberConfig contains the configuration items i.e. words for numbers etc.
 */

@Component
@Data
public class NumberConfig {

    @Value("#{'${words.basic.numbers}'.split(',')}")
    private List<String> basicNumberWords;

    @Value("#{'${words.tens.numbers}'.split(',')}")
    private List<String> tensNumberWords;

    @Value("${words.tens.join.char}")
    private String joinCharacterForTens;

    @Value("${words.negative}")
    private String negativeText;

}
