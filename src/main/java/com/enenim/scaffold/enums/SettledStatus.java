package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum SettledStatus  implements PersistableEnum<Integer> {
    UNSETTLED(0),
    SETTLED(1),
    FAILED(2),
    QUARANTINED(3);

    private Integer value;

    SettledStatus(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
    
    public static class Converter extends EnumValueTypeConverter<SettledStatus, Integer> {
        public Converter() {
            super(SettledStatus.class);
        }
    }
}