package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum SentSurcharge implements PersistableEnum<Integer> {
    SURCHARGE_NOT_SENT(0),
    SURCHARGE_SENT(1);
    
    Integer value;
    public Integer getValue(){
        return value;
    }

    SentSurcharge(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<SentSurcharge, Integer> {
        public Converter() {
            super(SentSurcharge.class);
        }
    }
}
