package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum Gender implements PersistableEnum<Integer> {
    MALE(0),
    FEMALE(1);

    Integer value;
    public Integer getValue(){
        return value;
    }

    Gender(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<Gender, Integer> {
        public Converter() {
            super(Gender.class);
        }
    }
}