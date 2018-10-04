package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

import javax.persistence.AttributeConverter;

public abstract class EnumValueTypeConverter<T extends Enum<T> & PersistableEnum<E>, E> implements AttributeConverter<T, E> {
    private final Class<T> clazz;

    EnumValueTypeConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        T[] enums = clazz.getEnumConstants();

        for (T e : enums) {
            if (e.getValue().equals(dbData)) {
                return e;
            }
        }
        throw new UnsupportedOperationException();
    }
}