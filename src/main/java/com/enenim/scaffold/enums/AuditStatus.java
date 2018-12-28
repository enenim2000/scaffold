package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum AuditStatus implements PersistableEnum<Integer> {
    REVOKED(0),
    ACTIVE(1),
    LOG(2),
    AWAITING_AUTHORIZATION(3);

    Integer value;
    public Integer getValue(){
        return value;
    }

    AuditStatus(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<AuditStatus, Integer> {
        public Converter() {
            super(AuditStatus.class);
        }
    }
}
