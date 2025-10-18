package com.test.rule;

import lombok.extern.slf4j.Slf4j;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kie.builder.impl.KieModuleKieProject;
import org.drools.model.codegen.ExecutableModelProject;
import org.drools.model.codegen.execmodel.CanonicalModelKieProject;
import org.drools.ruleunits.api.RuleUnit;
import org.drools.ruleunits.api.RuleUnitData;
import org.drools.ruleunits.impl.InternalRuleUnit;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.io.ResourceType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class RuleLoader {

    private final Map<String, RuleUnit<RuleUnitData>> ruleUnits = new HashMap<>();

    public void newRuleUnit() throws IOException {

        KieModuleKieProject kieModuleKieProject = compileDrl();

        ClassLoader classLoader = kieModuleKieProject.getClassLoader();

        for (RuleUnit impl : ServiceLoader.load(RuleUnit.class, classLoader)) {
            String ruleUnitNameForLoader = ((InternalRuleUnit<RuleUnitData>) impl).getRuleUnitDataClass().getCanonicalName();
            ruleUnits.put(ruleUnitNameForLoader, impl);
        }
    }

    public KieModuleKieProject compileDrl() throws IOException {
        KieServices ks = KieServices.get();
        KieFileSystem kfs = ks.newKieFileSystem();

        String drlPath = "/rules/rule2.drl";
        String drlContent = new String(getClass().getResourceAsStream(drlPath).readAllBytes(), StandardCharsets.UTF_8);

        kfs.write("src/main/resources/test/drl" + drlPath, drlContent);

        // Compile via kie internals
        InternalKieModule kieModule = (InternalKieModule) ks.newKieBuilder(kfs).getKieModule(ExecutableModelProject.class);

        return new CanonicalModelKieProject(kieModule, kieModule.getModuleClassLoader());
    }
}
