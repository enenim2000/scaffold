package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum TransactionDemoStatus implements PersistableEnum<Integer> {
    PAID(0),
    PENDING(1),
    FAILED(2),
    REVERSED(3);

    private Integer value;


    public Integer getValue(){
        return value;
    }

    TransactionDemoStatus(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<TransactionDemoStatus, Integer> {
        public Converter() {
            super(TransactionDemoStatus.class);
        }
    }
}