package com.enenim.scaffold.enums;

import com.enenim.scaffold.interfaces.PersistableEnum;
import com.enenim.scaffold.shared.KeyValue;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Enenim
 */
public enum SettingCategory implements PersistableEnum<String> {
    USER_SECURITY("User Security"),
    CONTACT_INFORMATION("Contact Information"),
    SECURITY_CENTER("Security Center"),
    ACTION_CENTER("Action Center"),
    LANGUAGE_SUPPORT("Language Support"),
    SETTLEMENT_PREFERENCE("Settlement Preference");

    private final String value;

    SettingCategory(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static List<KeyValue>  getAll(){
        List<KeyValue> values = new ArrayList<>();
        for(SettingCategory value : SettingCategory.values()){
            values.add(new KeyValue(value.toString(), value.value));
        }
        return values;
    }

    public String getValue(){
        return value;
    }
    

    public static class Converter extends EnumValueTypeConverter<SettingCategory, String> {
        public Converter() {
            super(SettingCategory.class);
        }
    }
}