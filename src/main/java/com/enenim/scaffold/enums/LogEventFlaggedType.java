package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum LogEventFlaggedType implements PersistableEnum<Integer> {
    FRAUDULENT(0),
    SUSPICIOUS(1),
    NORMAL(2);

    Integer value;
    public Integer getValue(){
        return value;
    }

    LogEventFlaggedType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<LogEventFlaggedType, Integer> {
        public Converter() {
            super(LogEventFlaggedType.class);
        }
    }
}
