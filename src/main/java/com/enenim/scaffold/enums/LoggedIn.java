package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum LoggedIn  implements PersistableEnum<Integer> {
    USER_NOT_LOGGED_IN(0),
    USER_LOGGED_IN(1);

    Integer value;
    public Integer getValue(){
        return value;
    }

    LoggedIn(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<LoggedIn, Integer> {
        public Converter() {
            super(LoggedIn.class);
        }
    }
}