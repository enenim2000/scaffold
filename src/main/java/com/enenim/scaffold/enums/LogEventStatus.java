package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum LogEventStatus  implements PersistableEnum<Integer> {
    NOT_SUCCESSFUL(0),
    SUCCESSFUL(1);

    Integer value;
    public Integer getValue(){
        return value;
    }

    LogEventStatus(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<LogEventStatus, Integer> {
        public Converter() {
            super(LogEventStatus.class);
        }
    }
}
