package com.enenim.scaffold.interceptor;

import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.enums.AuthorizationStatus;
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
import java.io.IOException;

import static com.enenim.scaffold.constant.CommonConstant.PLACE_HOLDER;
import static com.enenim.scaffold.constant.ModelFieldConstant.*;

@Aspect
@Service
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

    private Object intercept(ProceedingJoinPoint jp, Object entity, Audit audit) throws IOException {
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
        Object entityAfter = entity;

        if(entity instanceof IAuthorization && !skipAuthorization){

            System.out.println("About to save authorization");
            authorizationOperation();

            if(toggle != null){
                String newStatus = toggle.getNewStatus();
                if(EnabledStatus.ENABLED.name().equalsIgnoreCase(newStatus) || EnabledStatus.PENDING_ENABLED.name().equalsIgnoreCase(newStatus)){
                    response = "inactive";
                }else {
                    response = "active";
                }
                message = EntityMessage.msg("toggle_authorization_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
            }else if("Update".equalsIgnoreCase(audit.getCrudAction()) || "Delete".equalsIgnoreCase(audit.getCrudAction())){
                if("Update".equalsIgnoreCase(audit.getCrudAction())){
                    response = entity;
                    message = EntityMessage.msg("update_authorization_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                }else if("Delete".equalsIgnoreCase(audit.getCrudAction())) {
                    response = false;
                    message = EntityMessage.msg("delete_authorization_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                }
            }else {
                response = entity;
                message = EntityMessage.msg("create_authorization_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
            }
        }else {
            try {
                response = jp.proceed();
                RequestUtil.setAuthorization(null);
                entityAfter = response;
                if(toggle != null){
                    String newStatus = toggle.getNewStatus();
                    if(EnabledStatus.ENABLED.name().equalsIgnoreCase(newStatus) || EnabledStatus.PENDING_ENABLED.name().equalsIgnoreCase(newStatus)){
                        response = "active";
                    }else {
                        response = "inactive";
                    }
                    message = EntityMessage.msg("toggle_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                }else if("Update".equalsIgnoreCase(audit.getCrudAction()) || "Delete".equalsIgnoreCase(audit.getCrudAction())){
                    if("Update".equalsIgnoreCase(audit.getCrudAction())){
                        response = entityAfter;
                        message = EntityMessage.msg("update_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                    }else if("Delete".equalsIgnoreCase(audit.getCrudAction())){
                        response = true;
                        message = EntityMessage.msg("delete_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                    }
                }else {
                    message = EntityMessage.msg("create_message").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName()));
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        if(entity instanceof IAudit && !skipAudit){
            System.out.println("About to save audit");
            auditOperation(entity, entityAfter, audit);
        }
        
        RequestUtil.setMessage(message);
        ReflectionUtil.setFieldValue(entity.getClass(), TOGGLE, null, entity);

        return response;
    }

    private void auditOperation(Object entityBefore, Object entityAfter, Audit audit){
        audit.setIp(RequestUtil.getIpAddress());
        audit.setUserAgent(RequestUtil.getUserAgent());
        audit.setLogin(RequestUtil.getLogin());
        audit.setStatus(AuditStatus.ACTIVE);
        audit.setTaskRoute(RequestUtil.getTaskRoute());
        audit.setEntityType(entityAfter.getClass().getSimpleName());
        audit.setBefore((String) ReflectionUtil.getFieldValue(entityAfter.getClass(), BEFORE, entityBefore));
        audit.setAfter(JsonConverter.getJsonRecursive(entityAfter));
        audit.setDependency(null);
        audit.setAuthorization(RequestUtil.getAuthorization());
        audit.setStatus(RequestUtil.getAuditStatus());

        /*
         *This gives priority to userAction set at the controller level as compared to that done at the AuditAdvice class
         */
        if(!StringUtils.isEmpty(RequestUtil.getUserAction())){
            audit.setUserAction(RequestUtil.getUserAction());
        }

        auditService.saveAudit(audit);
    }

    private void authorizationOperation(){
        Authorization authorization = new Authorization();
        authorization.setTask(taskService.getTaskByRoute(RequestUtil.getTaskRoute()));
        authorization.setStatus(AuthorizationStatus.FORWARDED);
        authorization.setStaff(RequestUtil.getStaff());
        authorization.setRid(RequestUtil.getRID());
        authorization = authorizationService.saveAuthorization(authorization);
        RequestUtil.setAuthorization(authorization);
        RequestUtil.setAuditStatus(AuditStatus.AWAITING_AUTHORIZATION);
    }

    @Around("execution(* javax.persistence.EntityManager.persist(..)) && !execution(* javax.persistence.EntityManager.persist(com.enenim.scaffold.model.dao.Audit)) && !execution(* javax.persistence.EntityManager.persist(com.enenim.scaffold.model.dao.Authorization))" + " && args(entity,..)")
    public Object interceptCreate(ProceedingJoinPoint jp, Object entity) throws IOException {
        Audit audit = new Audit();
        audit.setCrudAction("Create");
        audit.setUserAction(EntityMessage.msg("audit_create").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName())));
        return intercept(jp, entity, audit);
    }

    @Around("execution(* javax.persistence.EntityManager.merge(..)) && !execution(* javax.persistence.EntityManager.merge(com.enenim.scaffold.model.dao.Audit)) && !execution(* javax.persistence.EntityManager.merge(com.enenim.scaffold.model.dao.Authorization))" + " && args(entity,..)")
    public Object interceptUpdate(ProceedingJoinPoint jp, Object entity) throws IOException {
        Audit audit = new Audit();
        audit.setCrudAction("Update");
        audit.setUserAction(EntityMessage.msg("audit_update").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName())));
        return intercept(jp, entity, audit);
    }

    @Around("execution(* javax.persistence.EntityManager.remove(..)) && !execution(* javax.persistence.EntityManager.remove(com.enenim.scaffold.model.dao.Audit)) && !execution(* javax.persistence.EntityManager.remove(com.enenim.scaffold.model.dao.Authorization))" + " && args(entity,..)")
    public Object interceptDelete(ProceedingJoinPoint jp, Object entity) throws IOException {
        Audit audit = new Audit();
        audit.setCrudAction("Delete");
        audit.setUserAction(EntityMessage.msg("audit_delete").replace(PLACE_HOLDER, EntityMessage.msg(entity.getClass().getSimpleName())));
        return intercept(jp, entity, audit);
    }
}