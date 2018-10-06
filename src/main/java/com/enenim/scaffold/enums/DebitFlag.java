package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum DebitFlag implements PersistableEnum<Integer> {
    EMPTY(0),
    SURCHARGE(1),
    BILLER_DISCOUNT(2);


    Integer value;
    public Integer getValue(){
        return value;
    }

    DebitFlag(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<DebitFlag, Integer> {
        public Converter() {
            super(DebitFlag.class);
        }
    }

}
