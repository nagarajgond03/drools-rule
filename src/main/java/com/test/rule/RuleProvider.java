package com.test.rule;

import com.test.drl.Channel;
import com.test.drl.DroolsFact;
import com.test.drl.PolicyUnit;
import org.drools.ruleunits.api.RuleUnitData;
import org.drools.ruleunits.api.RuleUnitInstance;

public class RuleProvider {
    public static void main(String[] args) throws Exception {
        String rule = "rule5.drl";
        RuleLoader ruleLoader = new RuleLoader();
        ruleLoader.newRuleUnit(rule);

        PolicyUnit unit = new PolicyUnit();
        Channel channel = new Channel();
        channel.setChannel("WEB");
        unit.getChannels().add(channel);
        unit.getFacts().add(new DroolsFact("device.ip.current.value.address", "1.1.1.1", String.class));

        try (RuleUnitInstance<RuleUnitData> instance = ruleLoader.newInstance(unit)) {
            instance.fire();
        }
    }
}
