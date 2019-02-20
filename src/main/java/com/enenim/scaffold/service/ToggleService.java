package com.enenim.scaffold.service;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.dao.Authorization;
import com.enenim.scaffold.model.dao.Vendor;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.shared.Toggle;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.ReflectionUtil;
import com.enenim.scaffold.util.message.PropertyUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static com.enenim.scaffold.constant.ModelFieldConstant.ENABLED;
import static com.enenim.scaffold.constant.ModelFieldConstant.TOGGLE;

@Data
public class ToggleService<T> {
    private EntityManager entityManager;
    private Class<T> type;

    public ToggleService(EntityManager entityManager, Class<T> type){
        this.entityManager = entityManager;
        this.type = type;
    }

    @Transactional
    public String toggle(Long id){
        return toggle(id, false, false);
    }

    @Transactional
    public String toggle(Long id, boolean skipAudit){
        return toggle(id, skipAudit, false);
    }

    @Transactional
    public String toggle(Long id, boolean skipAudit, boolean skipAuthorization){
        T entity = entityManager.find(type, id);
        Toggle toggle = new Toggle();

        toggle.setSkipAudit(skipAudit);
        toggle.setSkipAuthorization(skipAuthorization);

        if(entity instanceof Authorization)throw new ScaffoldException("toggle.authorization.not_allowed");

        Object message = PropertyUtil.msg("not_found").replace(CommonConstant.PLACE_HOLDER, PropertyUtil.msg(type.getSimpleName()));
        if(StringUtils.isEmpty(entity))throw new ScaffoldException(message);

        String objJson = JsonConverter.getJsonRecursive(entity) + "";
        String status = objJson.split(ENABLED)[1].replace("\":\"", "").split("\",")[0].trim();
        toggle.setOldStatus(status);

        String newStatus = "";

        if(!canToggle())throw new UnAuthorizedException("unauthorized");

        if(entity instanceof Vendor)vendorToggleCheck();
        if(entity instanceof Consumer)consumerToggleCheck();

        if(status.equalsIgnoreCase(EnabledStatus.ENABLED.name())){
            newStatus = EnabledStatus.DISABLED.name();
            toggle.setNewStatus(newStatus);
        }
        if(status.equalsIgnoreCase(EnabledStatus.DISABLED.name())) {
            newStatus = EnabledStatus.ENABLED.name();
            toggle.setNewStatus(newStatus);
        }
        if(status.equalsIgnoreCase(EnabledStatus.PENDING_ENABLED.name())){
            newStatus = EnabledStatus.DISABLED.name();
            toggle.setNewStatus(newStatus);
        }
        if(status.equalsIgnoreCase(EnabledStatus.PENDING_DISABLED.name())){
            newStatus = EnabledStatus.ENABLED.name();
            toggle.setNewStatus(newStatus);
        }

        objJson = objJson.replaceFirst(ENABLED+"\":\"" + status, ENABLED+"\":\""+newStatus);
        entity = JsonConverter.getObject(objJson, type);
        ReflectionUtil.setFieldValue(type, TOGGLE, toggle, entity);

        return (String) entityManager.merge(entity);
    }

    private void vendorToggleCheck(){

    }

    private void consumerToggleCheck(){

    }

    private boolean canToggle(){
        /*
         * Check to see
         */
        return true;
    }
}