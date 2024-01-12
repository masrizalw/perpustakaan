package com.perpustakaan.app.model;

import java.util.List;

import com.perpustakaan.app.enums.QueryOperator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Filter {

    private String field;
    private String value;
    private QueryOperator operator;
    private List<String> values;

}