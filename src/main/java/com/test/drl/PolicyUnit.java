package com.test.drl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

/**
 * A Rule Unit holding DataStores for use in DRL rules.
 * DataStores are exposed to DRL as: /results, /eventTypes, /channels, /configurationFact, /facts
 */
@Getter
@Setter
@RequiredArgsConstructor
public class PolicyUnit implements RuleUnitData {
    private final DataStore<DroolsResult> results;
    private final DataStore<DroolsEventType> eventTypes;
    private final DataStore<DroolsChannelIndicator> channels;
    private final DataStore<DroolsConfigurationFact> configurationFact;
    private final DataStore<DroolsFact> facts;
    private ListManager listManager;

    // Default constructor creates in-memory stores
    public PolicyUnit() {
        this(DataSource.createStore(), DataSource.createStore(), DataSource.createStore(), DataSource.createStore(), DataSource.createStore());
    }
}

