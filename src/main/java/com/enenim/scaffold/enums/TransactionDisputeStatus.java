package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum TransactionDisputeStatus  implements PersistableEnum<Integer> {
    CLOSED(0),
    OPEN(1);

    private Integer value;

    public Integer getValue(){
        return value;
    }

    TransactionDisputeStatus(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<TransactionDisputeStatus, Integer> {
        public Converter() {
            super(TransactionDisputeStatus.class);
        }
    }
}
