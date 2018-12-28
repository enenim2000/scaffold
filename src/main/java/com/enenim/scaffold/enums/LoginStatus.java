package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum LoginStatus implements PersistableEnum<Integer> {
    DISABLED(0),
    ENABLED(1),
    LOCKED(2),
    RESET(3),
    DORMANT(4);

    Integer value;
    public Integer getValue(){
        return value;
    }

    LoginStatus(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<LoginStatus, Integer> {
        public Converter() {
            super(LoginStatus.class);
        }
    }
}

