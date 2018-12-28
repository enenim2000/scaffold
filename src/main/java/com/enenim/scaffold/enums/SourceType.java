package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum SourceType implements PersistableEnum<Integer> {
    FIRSTPAY_PRE_LOGGED(0),
    FIRSTPAY_NON_LOGGED(1);

    private Integer value;
    
    public Integer getValue(){
        return value;
    }

    SourceType(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<SourceType, Integer> {
        public Converter() {
            super(SourceType.class);
        }
    }
}
