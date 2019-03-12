package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum TicketStatus implements PersistableEnum<Integer> {
    OPEN(0),
    CLOSED(1);

    private Integer value;

    public Integer getValue(){
        return value;
    }

    TicketStatus(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<TicketStatus, Integer> {
        public Converter() {
            super(TicketStatus.class);
        }
    }
}