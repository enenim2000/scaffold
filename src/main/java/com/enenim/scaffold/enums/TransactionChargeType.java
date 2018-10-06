package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum TransactionChargeType implements PersistableEnum<Integer> {
    EMPTY(0),
    PERCENTAGE_BASED(1),
    FIXED_VALUE(1);


    private Integer value;


    public Integer getValue(){
        return value;
    }

    TransactionChargeType(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<TransactionChargeType, Integer> {
        public Converter() {
            super(TransactionChargeType.class);
        }
    }
}
