package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum Initiator implements PersistableEnum<Integer> {
    USER(0),
    RECURRING_PAYMENT(1);

    Integer value;
    public Integer getValue(){
        return value;
    }

    Initiator(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<Initiator, Integer> {
        public Converter() {
            super(Initiator.class);
        }
    }
}
