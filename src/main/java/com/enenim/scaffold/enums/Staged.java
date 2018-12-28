package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum Staged implements PersistableEnum<Integer> {
    EMPTY(0),
    FIRST_NOTIFICATION(1),
    SECOND_NOTIFICATION(2),
    THIRD_NOTIFICATION(3);

    private Integer value;

    public Integer getValue(){
        return value;
    }

    Staged(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<Staged, Integer> {
        public Converter() {
            super(Staged.class);
        }
    }
}
