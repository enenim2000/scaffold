package com.enenim.scaffold.enums;


import com.enenim.scaffold.interfaces.PersistableEnum;

public enum ApprovalStatus implements PersistableEnum<Integer> {
    PENDING(0),
    APPROVED(1),
    REJECTED(2);

    Integer value;
    public Integer getValue(){
        return value;
    }

    ApprovalStatus(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<ApprovalStatus, Integer> {
        public Converter() {
            super(ApprovalStatus.class);
        }
    }
}
