package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum HolidayLogin  implements PersistableEnum<Integer> {
    NO(0),
    YES(1);
    
    Integer id;
    public Integer getValue(){
        return id;
    }

    HolidayLogin(Integer id) {
        this.id = id;
    }

    public static class Converter extends EnumValueTypeConverter<HolidayLogin, Integer> {
        public Converter() {
            super(HolidayLogin.class);
        }
    }
}