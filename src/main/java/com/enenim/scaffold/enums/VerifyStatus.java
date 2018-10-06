package com.enenim.scaffold.enums;


import com.enenim.scaffold.interfaces.PersistableEnum;

public enum VerifyStatus implements PersistableEnum<Integer> {
    NOT_VERIFIED(0),
    VERIFIED(1);

    private Integer value;

    public Integer getValue(){
        return value;
    }

    VerifyStatus(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<VerifyStatus, Integer> {
        public Converter() {
            super(VerifyStatus.class);
        }
    }
}