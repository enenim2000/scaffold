package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;

public enum TaskType implements PersistableEnum<Integer> {
    MENU(0),
    ACTION(1),
    PLUGIN_MENU(2),
    PLUGIN_ACTION(3);

    private Integer value;

    public Integer getValue(){
        return value;
    }

    TaskType(Integer value){
        this.value = value;
    }

    public static class Converter extends EnumValueTypeConverter<TaskType, Integer> {
        public Converter() {
            super(TaskType.class);
        }
    }
}
