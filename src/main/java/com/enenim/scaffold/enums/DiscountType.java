package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum DiscountType implements PersistableEnum<String> {
    EXTRA_CYCLE("extra_cycle"),
    FIXED("fixed"),
    PERCENTAGE("percentage");

    private String value;

    DiscountType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static class Converter extends EnumValueTypeConverter<DiscountType, String> {
        public Converter() {
            super(DiscountType.class);
        }
    }
}
