package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum BaseType implements PersistableEnum<Integer> {
    OTHERS(0),
    BASE_BANK(1);

    Integer value;
    public Integer getValue(){
        return value;
    }

    BaseType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<BaseType, Integer> {
        public Converter() {
            super(BaseType.class);
        }
    }
}
