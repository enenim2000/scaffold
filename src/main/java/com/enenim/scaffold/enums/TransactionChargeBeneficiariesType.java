package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum TransactionChargeBeneficiariesType implements PersistableEnum<Integer> {
    DEFAULT(0),
    FIXED(1),
    SOURCE(2);

    private Integer value;

    public Integer getValue(){
        return value;
    }

    TransactionChargeBeneficiariesType(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<TransactionChargeBeneficiariesType, Integer> {
        public Converter() {
            super(TransactionChargeBeneficiariesType.class);
        }
    }
}
