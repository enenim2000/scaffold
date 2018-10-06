package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum PayAgain implements PersistableEnum<Integer> {
    NO(0),
    YES(1);


    Integer value;
    public Integer getValue(){
        return value;
    }

    PayAgain(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<PayAgain, Integer> {
        public Converter() {
            super(PayAgain.class);
        }
    }
}
