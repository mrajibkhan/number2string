package com.onprem.rk.number2word.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Rajib Khan on 04 Feb 2018
 * ConversionResponse is the POJO container of input and output
 */

@Data
@AllArgsConstructor @NoArgsConstructor
public class ConversionResponse {
    private String input;
    private String output;
}
