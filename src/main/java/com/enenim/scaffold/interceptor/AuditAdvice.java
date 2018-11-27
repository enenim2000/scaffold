package com.enenim.scaffold.interceptor;

import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.interfaces.IAudit;
import com.enenim.scaffold.interfaces.IAuthorization;
import com.enenim.scaffold.model.dao.Audit;
import com.enenim.scaffold.model.dao.Authorization;
import com.enenim.scaffold.service.AuthorizationService;
import com.enenim.scaffold.service.dao.AuditService;
import com.enenim.scaffold.service.dao.TaskService;
import com.enenim.scaffold.shared.Toggle;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.ReflectionUtil;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.EntityMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.PersistenceContext;

import static com.enenim.scaffold.constant.CommonConstant.PLACE_HOLDER;
import static com.enenim.scaffold.constant.ModelFieldConstant.*;

@Service
@Aspect
@PersistenceContext
public class AuditAdvice {
   private final AuditService auditService;
   private final AuthorizationService authorizationService;
   private final TaskService taskService;

    @Autowired
    public AuditAdvice(AuditService auditService, AuthorizationService authorizationService, TaskService taskService) {
        this.auditService = auditService;
        this.authorizationService = authorizationService;
        this.taskService = taskService;
    }

    private Object intercept(ProceedingJoinPoint jp, Object entity, InterceptorParams params){
        Toggle toggle = (Toggle) ReflectionUtil.getFieldValue(entity.getClass(), TOGGLE, entity);
        boolean skipAudit, skipAuthorization;
        if(toggle != null){
            skipAudit = toggle.isSkipAudit();
            skipAuthorization = toggle.isSkipAuthorization();
        }else {
            skipAudit = (boolean)ReflectionUtil.getFieldValue(entity.getClass(), SKIP_AUDITING, entity);
            skipAuthorization = (boolean)ReflectionUtil.getFieldValue(entity.getClass(), SKIP_AUTHORIZATION, entity);
        }

        skipAuthorization = skipAudit && !skipAuthorization || skipAuthorization;

        Object response = null;
        String message = null;
        Object entityBefore = entity;
        Object entityAfter = entity;

        if(entity instanceof IAuthorization && !skipAuthorization){

            authorizationOperation(params);

            if(toggle != null){
                String newStatus = toggle.getNewStatus();
                if(EnabledStatus.ENABLED.name().equalsIgnoreCase(newStatus) || EnabledStatus.PENDING_ENABLED.name().equalsIgnoreCase(newStatus)){
                    response = "inactive";
                }else {
                    response = "active";
                }
                message = EntityMessage.msg("toggle_authorization_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
            }else if("Update".equalsIgnoreCase(params.getAudit().getCrudAction()) || "Delete".equalsIgnoreCase(params.getAudit().getCrudAction())){
                response = false;
                if("Update".equalsIgnoreCase(params.getAudit().getCrudAction())){
                    message = EntityMessage.msg("update_authorization_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                }else {
                    message = EntityMessage.msg("delete_authorization_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                }
            }else {
                response = entity;
                message = EntityMessage.msg("create_authorization_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
            }
        }else {
            try {
                response = jp.proceed();
                entityAfter = response;
                if(toggle != null){
                    String newStatus = toggle.getNewStatus();
                    if(EnabledStatus.ENABLED.name().equalsIgnoreCase(newStatus) || EnabledStatus.PENDING_ENABLED.name().equalsIgnoreCase(newStatus)){
                        response = "active";
                    }else {
                        response = "inactive";
                    }
                    message = EntityMessage.msg("toggle_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                }else if("Update".equalsIgnoreCase(params.getAudit().getCrudAction()) || "Delete".equalsIgnoreCase(params.getAudit().getCrudAction())){
                    response = true;
                    if("Update".equalsIgnoreCase(params.getAudit().getCrudAction())){
                        message = EntityMessage.msg("update_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                    }else {
                        message = EntityMessage.msg("delete_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                    }
                }else {
                    message = EntityMessage.msg("create_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        RequestUtil.setMessage(message);
        ReflectionUtil.setFieldValue(entity.getClass(), TOGGLE, null, entity);

        if(entity instanceof IAudit){
           Audit audit = auditOperation(entityBefore, entityAfter, params).getAudit();
           auditService.saveAudit(audit);
        }

        return response;
    }

    private InterceptorParams auditOperation(Object entityBefore, Object entityAfter, InterceptorParams params){
        params.getAudit().setIp(RequestUtil.getIpAddress());
        params.getAudit().setUserAgent(RequestUtil.getUserAgent());
        if(!StringUtils.isEmpty(RequestUtil.getUserAction())){
            params.getAudit().setUserAction(RequestUtil.getUserAction());
        }
        params.getAudit().setLogin(RequestUtil.getLogin());
        params.getAudit().setStatus(AuditStatus.ACTIVE);
        params.getAudit().setTaskRoute(RequestUtil.getTaskRoute());
        params.getAudit().setEntityType(entityAfter.getClass().getSimpleName());
        params.getAudit().setBefore((String) ReflectionUtil.getFieldValue(entityAfter.getClass(), BEFORE, entityBefore));
        params.getAudit().setAfter(JsonConverter.getJsonRecursive(entityAfter));

        params.getAudit().setDependency("");
        params.getAudit().setAuthorization(RequestUtil.getAuthorization());
        params.getAudit().setStatus(RequestUtil.getAuditStatus());

        return params;
    }

    private InterceptorParams authorizationOperation(Object entity, InterceptorParams params){
        Authorization authorization = new Authorization();
        authorization.setTask(taskService.getTaskByRoute(RequestUtil.getTaskRoute()));
        authorization.setStatus(RequestUtil.getAuthorizationStatus());
        authorization.setStaff(RequestUtil.getStaff());
        authorization.setRid(RequestUtil.getRID());
        authorization = authorizationService.saveAuthorization(authorization);
        RequestUtil.setAuthorization(authorization);
        RequestUtil.setAuditStatus(AuditStatus.AWAITING_AUTHORIZATION);
        return params;
    }

    @Around("execution(* javax.persistence.EntityManager.persist(..)) && !execution(* javax.persistence.EntityManager.persist(com.enenim.scaffold.model.dao.Audit)) && !execution(* javax.persistence.EntityManager.persist(com.enenim.scaffold.model.dao.Authorization))" + " && args(entity,..)")
    public Object interceptCreate(ProceedingJoinPoint jp, Object entity) {
        InterceptorParams params = new InterceptorParams();
        params.getAudit().setCrudAction("Create");
        params.getAudit().setUserAction("Created new " + entity.getClass().getSimpleName());
        return intercept(jp, entity, params);
    }

    @Around("execution(* javax.persistence.EntityManager.merge(..)) && !execution(* javax.persistence.EntityManager.merge(com.enenim.scaffold.model.dao.Audit)) && !execution(* javax.persistence.EntityManager.merge(com.enenim.scaffold.model.dao.Authorization))" + " && args(entity,..)")
    public Object interceptUpdate(ProceedingJoinPoint jp, Object entity) {
        InterceptorParams params = new InterceptorParams();
        params.getAudit().setCrudAction("Update");
        params.getAudit().setUserAction("Updated existing " + entity.getClass().getSimpleName());
        return intercept(jp, entity, params);
    }

    @Around("execution(* javax.persistence.EntityManager.remove(..)) && !execution(* javax.persistence.EntityManager.remove(com.enenim.scaffold.model.dao.Audit)) && !execution(* javax.persistence.EntityManager.remove(com.enenim.scaffold.model.dao.Authorization))" + " && args(entity,..)")
    public Object interceptDelete(ProceedingJoinPoint jp, Object entity) {
        InterceptorParams params = new InterceptorParams();
        params.getAudit().setCrudAction("Delete");
        params.getAudit().setUserAction("Deleted existing " + entity.getClass().getSimpleName());
        return intercept(jp, entity, params);
    }

    private class InterceptorParams{
        private Audit audit;
        private Authorization authorization;

        public Audit getAudit() {
            return StringUtils.isEmpty(audit) ? new Audit() : audit;
        }

        public void setAudit(Audit audit) {
            this.audit = audit;
        }

        public Authorization getAuthorization() {
            return StringUtils.isEmpty(authorization) ? new Authorization() : authorization;
        }

        public void setAuthorization(Authorization authorization) {
            this.authorization = authorization;
        }
    }
}