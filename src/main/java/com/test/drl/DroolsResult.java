package com.test.drl;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gorell
 * Date: 7/27/11
 * Time: 8:37 AM
 * Result of Drools Policy Engine run
 */
@Data
@NoArgsConstructor
public class DroolsResult
{
    private List<String> firedProductionRules = new ArrayList<>();

    private List<String> firedTestRules = new ArrayList<>();

    public void addFiredProductionRule(String rule)
    {
        firedProductionRules.add(rule);
    }

    public void addFiredTestRule(String rule)
    {
        firedTestRules.add(rule);
    }
}
