package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum AuthorizationStatus implements PersistableEnum<Integer> {
    NOT_FORWARDED(0),
    FORWARDED(1),
    PENDING(2),
    AUTHORIZED(3),
    NOT_AUTHORIZED(4),
    REJECTED(5);
    
    Integer value;
    public Integer getValue(){
        return value;
    }

    AuthorizationStatus(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<AuthorizationStatus, Integer> {
        public Converter() {
            super(AuthorizationStatus.class);
        }
    }
}

