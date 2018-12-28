package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum BillerType implements PersistableEnum<Integer> {
    REGULAR(0),
    AGGREGATOR(1),
    AGGREGATE(2);

    Integer value;
    public Integer getValue(){
        return value;
    }

    BillerType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<BillerType, Integer> {
        public Converter() {
            super(BillerType.class);
        }
    }
}
