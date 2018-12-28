package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum ChargeSource implements PersistableEnum<String> {
    AMOUNT("amount"),
    BALANCE("balance");

    private String value;

    ChargeSource(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }


    public static class Converter extends EnumValueTypeConverter<ChargeSource, String> {
        public Converter() {
            super(ChargeSource.class);
        }
    }
}
