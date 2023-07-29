package com.hcc.hccservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResult {

    private String name;
    private int age;
    private String gender;
    private CommunityNameValuePair communityNameValuePair;
    private List<CodeValuePair> codeValuePairs;
    private double total;

}
