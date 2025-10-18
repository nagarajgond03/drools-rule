package com.test.drl;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DroolsResult
{
    private List<String> firedRules = new ArrayList<>();

    public void addFiredRule(String rule)
    {
        firedRules.add(rule);
    }

}
