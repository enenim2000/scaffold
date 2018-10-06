package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum PayAgainType  implements PersistableEnum<Integer> {
    EMPTY(0),
    CONTINUE(1),
    RESTART(2);
    
    Integer value;
    public Integer getValue(){
        return value;
    }

    PayAgainType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<PayAgainType, Integer> {
        public Converter() {
            super(PayAgainType.class);
        }
    }
}
