package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum RequestType  implements PersistableEnum<Integer> {
    EMPTY(0),
    CREATE_CONSUMER(1),
    MAKE_PAYMENT(2),
    CANCEL_PAYMENT(3);


    Integer value;
    public Integer getValue(){
        return value;
    }

    RequestType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<RequestType, Integer> {
        public Converter() {
            super(RequestType.class);
        }
    }
}
