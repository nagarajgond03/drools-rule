package com.test.rule;

import lombok.extern.slf4j.Slf4j;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kie.builder.impl.KieModuleKieProject;
import org.drools.model.codegen.ExecutableModelProject;
import org.drools.model.codegen.execmodel.CanonicalModelKieProject;
import org.drools.ruleunits.api.RuleUnit;
import org.drools.ruleunits.api.RuleUnitData;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.impl.InternalRuleUnit;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

@Slf4j
public class RuleLoader {

    private final Map<String, RuleUnit<RuleUnitData>> ruleUnits = new HashMap<>();

    public RuleUnitInstance<RuleUnitData> newInstance(RuleUnitData ruleUnitData) {
        RuleUnit<RuleUnitData> ruleUnit = getRuleUnit(ruleUnitData);
        return ruleUnit.createInstance(ruleUnitData);
    }

    public RuleUnit<RuleUnitData> getRuleUnit(RuleUnitData ruleUnitData) {
        String ruleUnitName = ruleUnitData.getClass().getCanonicalName();
        RuleUnit<RuleUnitData> ruleUnit = ruleUnits.get(ruleUnitName);
        if (ruleUnit == null) {
            log.info("No existing rule unit for {}, creating new one", ruleUnitName);
        }
        return ruleUnit;
    }

    public void newRuleUnit(String ruleName) throws IOException {
        long s = System.currentTimeMillis();
        KieModuleKieProject kieModuleKieProject = compileDrl(ruleName);
        System.out.println("Compilation took " + (System.currentTimeMillis() - s) + "ms " + "for " + ruleName);

        s = System.currentTimeMillis();
        ClassLoader classLoader = kieModuleKieProject.getClassLoader();

        for (RuleUnit impl : ServiceLoader.load(RuleUnit.class, classLoader)) {
            String ruleUnitNameForLoader = ((InternalRuleUnit<RuleUnitData>) impl).getRuleUnitDataClass().getCanonicalName();
            ruleUnits.put(ruleUnitNameForLoader, impl);
        }

        System.out.println("Loading rule units took " + (System.currentTimeMillis() - s) + "ms " + "for " + ruleName);
    }

    public KieModuleKieProject compileDrl(String ruleName) throws IOException {
        System.setProperty("drools.lang.level", "DRL10");
        KieServices ks = KieServices.get();
        KieFileSystem kfs = ks.newKieFileSystem();

        String drlPath = "/rules/" + ruleName;
        String drlContent = new String(getClass().getResourceAsStream(drlPath).readAllBytes(), StandardCharsets.UTF_8);

        kfs.write("src/main/resources/test/drl" + drlPath, drlContent);

        // Compile via kie internals
        InternalKieModule kieModule = (InternalKieModule) ks.newKieBuilder(kfs).getKieModule(ExecutableModelProject.class);


        return new CanonicalModelKieProject(kieModule, kieModule.getModuleClassLoader());
    }

}
