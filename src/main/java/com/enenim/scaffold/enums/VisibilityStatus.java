package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum VisibilityStatus  implements PersistableEnum<Integer> {
    NO(0),
    YES(1);
    
    private Integer value;

    public Integer getValue(){
        return value;
    }

    VisibilityStatus(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<VisibilityStatus, Integer> {
        public Converter() {
            super(VisibilityStatus.class);
        }
    }
}
