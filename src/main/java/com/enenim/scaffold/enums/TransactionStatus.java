package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum TransactionStatus implements PersistableEnum<Integer> {

    PAID(0),
    PENDING(1),
    FAILED(2),
    REVERSED(3),
    PARTIAL(4);

    private Integer value;

    public Integer getValue(){
        return value;
    }

    TransactionStatus(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<TransactionStatus, Integer> {
        public Converter() {
            super(TransactionStatus.class);
        }
    }

}
