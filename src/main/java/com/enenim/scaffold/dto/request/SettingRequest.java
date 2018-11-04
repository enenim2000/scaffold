package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.constant.ValidationConstant;
import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.shared.ErrorValidator;
import com.enenim.scaffold.shared.Validation;
import com.enenim.scaffold.util.message.ValidationMessage;
import com.enenim.scaffold.util.setting.SettingCacheCoreUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SettingRequest extends RequestBody<Setting> {
    @Transient
    public static final String MODEL_KEY = "setting";

    @Transient
    public static final String MODEL_KEY_INVALID = MODEL_KEY + ".id.invalid";

    @Transient
    public static final String KEY = "key";

    @Transient
    public static final String VALUE = "value";

    @NotNull
    @NotBlank
    private String key;

    @NotNull
    @NotBlank
    private String value;

    @Override
    public Setting buildModel() {
        return null;
    }

    @Override
    public Setting buildModel(Setting model) {
        return null;
    }

    @Override
    public Validation validateRequest() {
        ErrorValidator errorValidator = new ErrorValidator();

        SettingCache settingCache = SettingCacheCoreUtil.getSettingMap().get(this.key);
        
        String[] arrays = settingCache.getValidation() == null ? new String[]{} : settingCache.getValidation().split("\\|");
        String required = "required";//or optional
        String type;
        int min;
        int max;
        int requiredIndex = 0, typeIndex = 1, minIndex = 2, maxIndex = 3;

        if(this.getValue() == null || this.getValue().trim().isEmpty()){
            errorValidator.addError(Validation.validateInput(this.getValue(), MODEL_KEY, VALUE,  ValidationConstant.TYPE_REQUIRED));
            return new Validation(errorValidator.build());
        }

        if(arrays.length >= 1 && required.equalsIgnoreCase(arrays[requiredIndex])){
            errorValidator.addError(Validation.validateInput(this.getValue(), MODEL_KEY, VALUE,  ValidationConstant.TYPE_REQUIRED));
        }

        if(!StringUtils.isEmpty(settingCache.getOptions())){
            if(!settingCache.getOptions().containsKey(this.getKey())){
                errorValidator.addError("options", "required", ValidationMessage.msg("setting.options.invalid"));
            }else {
                /*Discard request value for system configured value for settings that have options */
                this.setValue(settingCache.getOptions().get(this.getKey()));
            }
        }

        if (arrays.length >= 2){
            type = arrays[typeIndex];
            if(ValidationConstant.TYPE_INTEGER.equalsIgnoreCase(type)){
                errorValidator.addError(Validation.validateInput(this.getValue(), MODEL_KEY, VALUE, ValidationConstant.TYPE_INTEGER));
            }

            if(ValidationConstant.TYPE_FLOAT.equalsIgnoreCase(type)){
                errorValidator.addError(Validation.validateInput(this.getValue(), MODEL_KEY, VALUE, ValidationConstant.TYPE_FLOAT));
            }
        }

        if (arrays.length >= 4  && ValidationConstant.TYPE_MIN.equalsIgnoreCase(arrays[minIndex].split(":")[0]) && ValidationConstant.TYPE_MAX.equalsIgnoreCase(arrays[maxIndex].split(":")[0])){
            //required|integer|min:3|max:10
            min = Integer.valueOf( arrays[minIndex].split(":")[1] );
            max = Integer.valueOf( arrays[maxIndex].split(":")[1] );
            if( !(Integer.valueOf(this.getValue().trim()) >= min && Integer.valueOf(this.getValue().trim()) <= max)){
                errorValidator.addError(Validation.validateInput(this.getValue(), MODEL_KEY, VALUE, ValidationConstant.TYPE_MINMAX, min, max));
            }
        }else if (arrays.length >= 4 && ValidationConstant.TYPE_MAX.equalsIgnoreCase(arrays[maxIndex].split(":")[0])){
            //required|integer|min:3|max:10
            max = Integer.valueOf( arrays[maxIndex].split(":")[1] );
            if(Integer.valueOf(this.getValue().trim()) > max){
                errorValidator.addError(Validation.validateInput(this.getValue(), MODEL_KEY, VALUE, ValidationConstant.TYPE_MAX, max));
            }
        }else if (arrays.length >= 3 && ValidationConstant.TYPE_MAX.equalsIgnoreCase(arrays[minIndex].split(":")[0])){
            //required|integer|max:10
            max = Integer.valueOf( arrays[minIndex].split(":")[1] );
            if(Integer.valueOf(this.getValue().trim()) > max){
                errorValidator.addError(Validation.validateInput(this.getValue(), MODEL_KEY, VALUE, ValidationConstant.TYPE_MAX, max));
            }
        }else if (arrays.length >= 3 && ValidationConstant.TYPE_MIN.equalsIgnoreCase(arrays[minIndex].split(":")[0])){
            //required|integer|min:3
            min = Integer.valueOf( arrays[minIndex].split(":")[1] );
            if(Integer.valueOf(this.getValue().trim()) < min){
                errorValidator.addError(Validation.validateInput(this.getValue(), MODEL_KEY, VALUE, ValidationConstant.TYPE_MIN, min));
            }
        }

        return new Validation(errorValidator.build());
    }
}