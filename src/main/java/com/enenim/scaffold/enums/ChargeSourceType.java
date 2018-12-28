package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum ChargeSourceType implements PersistableEnum<Integer> {
    AMOUNT(0),
    BALANCE(1);


    Integer value;
    public Integer getValue(){
        return value;
    }

    ChargeSourceType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<ChargeSourceType, Integer> {
        public Converter() {
            super(ChargeSourceType.class);
        }
    }
}


