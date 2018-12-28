package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum WeekendLogin implements PersistableEnum<Integer> {
    NO(0),
    YES(1);
    
    private Integer id;

    public Integer getValue(){
        return id;
    }

    WeekendLogin(Integer id){
        this.id = id;
    }

    public static class Converter extends EnumValueTypeConverter<VisibilityStatus, Integer> {
        public Converter() {
            super(VisibilityStatus.class);
        }
    }
}
