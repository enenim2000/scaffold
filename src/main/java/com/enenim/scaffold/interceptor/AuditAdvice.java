package com.enenim.scaffold.interceptor;

import com.enenim.scaffold.annotation.Permission;
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
import javax.persistence.Table;

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

        if(entity instanceof IAudit){
            auditOperation(jp, entity, params);
        }

        if(entity instanceof IAuthorization){
            authorizationOperation(jp, entity, params);
        }

        Object response = null;
        String message = null;

        if(entity instanceof IAuthorization && !skipAuthorization){
            if(toggle != null){
                String newStatus = toggle.getNewStatus();
                if(EnabledStatus.ENABLED.name().equalsIgnoreCase(newStatus) || EnabledStatus.PENDING_ENABLED.name().equalsIgnoreCase(newStatus)){
                    response = "inactive";
                }else {
                    response = "active";
                }
                message = EntityMessage.msg("toggle_authorization_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
            }else if("Update".equalsIgnoreCase(params.getAudit().getAction()) || "Delete".equalsIgnoreCase(params.getAudit().getAction())){
                response = false;
                if("Update".equalsIgnoreCase(params.getAudit().getAction())){
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
                if(toggle != null){
                    String newStatus = toggle.getNewStatus();
                    if(EnabledStatus.ENABLED.name().equalsIgnoreCase(newStatus) || EnabledStatus.PENDING_ENABLED.name().equalsIgnoreCase(newStatus)){
                        response = "active";
                    }else {
                        response = "inactive";
                    }
                    message = EntityMessage.msg("toggle_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                }else if("Update".equalsIgnoreCase(params.getAudit().getAction()) || "Delete".equalsIgnoreCase(params.getAudit().getAction())){
                    response = true;
                    if("Update".equalsIgnoreCase(params.getAudit().getAction())){
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

        /*
         * This message is stored here to be retrieved in ResponseWrapper<T>
         */
        RequestUtil.setMessage(message);
        ReflectionUtil.setFieldValue(entity.getClass(), TOGGLE, null, entity);
        return response;
    }

    private void auditOperation(ProceedingJoinPoint jp, Object entity, InterceptorParams params){
        params.getAudit().setIp(RequestUtil.getIpAddress());
        params.getAudit().setUserAction(RequestUtil.getUserAction());
        params.getAudit().setLogin(RequestUtil.getLogin());
        params.getAudit().setTrailId(RequestUtil.getLogin().getUserId());
        params.getAudit().setTrailType(RequestUtil.getLogin().getUserType());
        params.getAudit().setUserName(RequestUtil.getLogin().getUsername());
        if(entity instanceof IAuthorization) params.getAudit().setRid(null);
        else params.getAudit().setRid(RequestUtil.getRID());
        params.getAudit().setStatus(AuditStatus.ACTIVE);
        params.getAudit().setTableName(entity.getClass().getAnnotation(Table.class).name());
        params.getAudit().setTaskRoute(RequestUtil.getTaskRoute());
        params.getAudit().setTrailType(entity.getClass().getSimpleName());
    }

    private void authorizationOperation(ProceedingJoinPoint jp, Object entity, InterceptorParams params){
        if(RequestUtil.getAuthorizationId() != null) params.getAudit().setAuthorization(authorizationService.getAuthorization(RequestUtil.getAuthorizationId()));
        Authorization authorization = new Authorization();
        authorization.setAction("");//
        authorization.setComment("");//Should be null but holds "Reject" if the authorization is rejected
        authorization.setTask(taskService.getTaskByRoute(RequestUtil.getTaskRoute()));
        //if(systemSettingService.getSetting(key) == "not_forwarded")authorization.setStatus(AuthorizationStatus.NOT_FORWARDED);
        //if(systemSettingService.getSetting(key) == "pending")authorization.setStatus(AuthorizationStatus.PENDING);
        authorization.setStaff(RequestUtil.getStaff());
        authorization.setRid(RequestUtil.getRID());
        params.getAudit().setDependency("item.id");
        params.getAudit().setStatus(AuditStatus.AWAITING_AUTHORIZATION);
        params.getAudit().setAuthorization(authorization);
    }

    @Around("execution(* javax.persistence.EntityManager.persist(..)) && !execution(* javax.persistence.EntityManager.persist(com.enenim.scaffold.model.dao.Audit)) && !execution(* javax.persistence.EntityManager.persist(com.enenim.scaffold.model.dao.Authorization))" + " && args(entity,..)")
    public Object interceptCreate(ProceedingJoinPoint jp, Object entity) {
        InterceptorParams params = new InterceptorParams();
        params.getAudit().setAction("Create");
        params.getAudit().setTrailId(null);
        params.getAudit().setBefore(null);
        params.getAudit().setAfter(JsonConverter.getJsonRecursive(entity));
        return intercept(jp, entity, params);
    }

    @Around("execution(* javax.persistence.EntityManager.merge(..)) && !execution(* javax.persistence.EntityManager.merge(com.enenim.scaffold.model.dao.Audit)) && !execution(* javax.persistence.EntityManager.merge(com.enenim.scaffold.model.dao.Authorization))" + " && args(entity,..)")
    public Object interceptUpdate(ProceedingJoinPoint jp, Object entity) {
        InterceptorParams params = new InterceptorParams();
        params.getAudit().setAction("Update");
        params.getAudit().setTrailId( (Long) ReflectionUtil.getFieldValue(entity.getClass(), ID, entity) );
        params.getAudit().setBefore((String) ReflectionUtil.getFieldValue(entity.getClass(), BEFORE, entity));
        params.getAudit().setAfter(JsonConverter.getJsonRecursive(entity));
        return intercept(jp, entity, params);
    }

    @Around("execution(* javax.persistence.EntityManager.remove(..)) && !execution(* javax.persistence.EntityManager.remove(com.enenim.scaffold.model.dao.Audit)) && !execution(* javax.persistence.EntityManager.remove(com.enenim.scaffold.model.dao.Authorization))" + " && args(entity,..)")
    public Object interceptDelete(ProceedingJoinPoint jp, Object entity) {
        InterceptorParams params = new InterceptorParams();
        params.getAudit().setAction("Delete");
        params.getAudit().setTrailId( (Long) ReflectionUtil.getFieldValue(entity.getClass(), ID, entity) );
        params.getAudit().setBefore(JsonConverter.getJsonRecursive(entity));
        params.getAudit().setAfter(null);
        params.getAudit().setRid(RequestUtil.getRID());
        params.getAudit().setTaskRoute(RequestUtil.getTaskRoute());
        params.getAudit().setUserAction(RequestUtil.getUserAction());
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