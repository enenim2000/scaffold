package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum LogEventType  implements PersistableEnum<Integer> {
    FRAUD(0),
    INVALID(1),
    EXPIRED(2),
    UNAUTHORIZED(3),
    SENSITIVE(4),
    ORDINARY(5);

    Integer value;
    public Integer getValue(){
        return value;
    }

    LogEventType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<LogEventType, Integer> {
        public Converter() {
            super(LogEventType.class);
        }
    }
}
