package com.enenim.scaffold.service;

import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.interfaces.IAudit;
import com.enenim.scaffold.repository.BaseRepository;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.ReflectionUtil;
import com.enenim.scaffold.util.message.EntityMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static com.enenim.scaffold.constant.CommonConstant.NOT_FOUND;


@SuppressWarnings("SpringJavaAutowiringInspection")
public class BaseRepositoryService<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private EntityManager entityManager;
    private JpaEntityInformation<T, ID> entityInformation;

    public BaseRepositoryService(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(entityInformation.getJavaType(), entityManager), entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    public BaseRepositoryService(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    public T findOrFail(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        Class<T> domainType = this.getDomainClass();
        T entity = Optional.ofNullable(this.entityManager.find(domainType, id)).orElse(null);
        if(entity == null){
            Object message = EntityMessage.msg(NOT_FOUND).replaceFirst("\\{}", EntityMessage.msg(domainType.getSimpleName())).replaceFirst("\\{}", id+"");
            throw new ScaffoldException(message);
        }

        if(entity instanceof IAudit){
            entity = savePrevious(domainType, entity);
        }

        return entity;
    }

    public T find(ID id) {
        T entity = this.entityManager.find(this.getDomainClass(), id);
        if(entity != null && entity instanceof IAudit){
            return savePrevious(this.getDomainClass(), entity);
        }
        return null;
    }

    /**
     *
     * @param domainType is Model Class
     * @param entity is object from the database
     * @return T: the modified entity that now contains the T entity saved in before property field of the model
     */
    private T savePrevious(Class<T> domainType, T entity){
        return (T)ReflectionUtil.setFieldValue(domainType, JsonConverter.getJsonRecursive(entity), entity);
    }

    @Transactional
    @Override
    public List<T> search(Pageable pageable) {
        return null;
    }

    @Transactional
    public Object toggle(Long id){
        return new ToggleService<>(entityManager, this.getDomainClass()).toggle(id);
    }

    @Transactional
    public Object toggle(Long id, boolean skipAudit){
        return new ToggleService<>(entityManager, this.getDomainClass()).toggle(id, skipAudit);
    }

    @Transactional
    public Object toggle(Long id, boolean skipAudit, boolean skipAuthorization){
        return new ToggleService<>(entityManager, this.getDomainClass()).toggle(id, skipAudit, skipAuthorization);
    }
}

