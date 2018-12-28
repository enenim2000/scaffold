package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum AmountType  implements PersistableEnum<Integer> {
    DEFAULT(0),
    FIXED_AMOUNT(1),
    VARIABLE_AMOUNT(2);

    Integer value;
    public Integer getValue(){
        return value;
    }

    AmountType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<AmountType, Integer> {
        public Converter() {
            super(AmountType.class);
        }
    }
}