package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;
import lombok.Getter;

@Getter
public enum EnabledStatus  implements PersistableEnum<Integer> {
    DISABLED(0),
    ENABLED(1),
    PENDING_DISABLED(2),
    PENDING_ENABLED(3),
    BACK_OFFICE_DISABLED(4);

    public static EnabledStatus validate(String param){
        EnabledStatus status = null;
        for(EnabledStatus value : EnabledStatus.values()){
            if(value == EnabledStatus.valueOf(param.toUpperCase())){
                status = value;
            }
        }
        return status;
    }

    Integer value;
    public Integer getValue(){
        return value;
    }

    EnabledStatus(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<EnabledStatus, Integer> {
        public Converter() {
            super(EnabledStatus.class);
        }
    }
}
