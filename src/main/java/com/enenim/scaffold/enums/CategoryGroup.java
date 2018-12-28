package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum CategoryGroup implements PersistableEnum<Integer> {
    DEFAULT(0),
    PAY(1),
    BOOK(2);

    private Integer value;

    CategoryGroup(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }


    public static class Converter extends EnumValueTypeConverter<CategoryGroup, Integer> {
        public Converter() {
            super(CategoryGroup.class);
        }
    }
}
