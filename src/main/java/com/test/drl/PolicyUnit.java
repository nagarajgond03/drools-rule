package com.test.drl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

@Getter
@Setter
@RequiredArgsConstructor
public class PolicyUnit implements RuleUnitData {
    private final DataStore<DroolsResult> results;
    private final DataStore<Event> eventTypes;
    private final DataStore<Channel> channels;
    private final DataStore<DroolsFact> facts;
    private ListManager listManager;

    // Default constructor creates in-memory stores
    public PolicyUnit() {
        this(DataSource.createStore(), DataSource.createStore(), DataSource.createStore(), DataSource.createStore());
    }
}

