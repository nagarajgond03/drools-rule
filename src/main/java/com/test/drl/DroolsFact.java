package com.test.drl;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode
public class DroolsFact {
    private final String name;
    private final Object value;
    private final String valueStr;
    private final Object type;
    @Setter
    private boolean preprocess1;
    @Setter
    private boolean preprocess2;
    @Setter
    private boolean preprocess3;
    @Setter
    private boolean preprocess4;

    public DroolsFact(String name, Object value, Object type) {
        this.name = name;
        this.value = value;
        this.valueStr = value instanceof String ? (String) value : value.toString();
        this.type = type;
    }

}

