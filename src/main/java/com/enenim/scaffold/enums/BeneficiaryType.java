package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

/*
 * Flag for global beneficiaries
 */
public enum BeneficiaryType implements PersistableEnum<Integer> {
    GLOBAL(0),
    REGULAR(1);

    Integer value;

    public Integer getValue() {
        return value;
    }

    BeneficiaryType(Integer value) {
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<BeneficiaryType, Integer> {
        public Converter() {
            super(BeneficiaryType.class);
        }
    }
}
