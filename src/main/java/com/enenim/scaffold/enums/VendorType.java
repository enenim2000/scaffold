package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum VendorType implements PersistableEnum<Integer> {
    REGULAR(0),
    AGGREGATOR(1),
    AGGREGATE(2);

    Integer value;
    public Integer getValue(){
        return value;
    }

    VendorType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<VendorType, Integer> {
        public Converter() {
            super(VendorType.class);
        }
    }
}
