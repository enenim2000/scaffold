
package com.enenim.scaffold.model;

import com.enenim.scaffold.constant.ModelFieldConstant;
import com.enenim.scaffold.dto.response.ResponseBody;
import com.enenim.scaffold.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@MappedSuperclass
@JsonInclude(JsonInclude.Include.ALWAYS)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "hibernateLazyInitializer", "handler"})
public abstract class BaseModel extends ResponseBody implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonProperty(ModelFieldConstant.CREATED_AT)
    @CreatedDate
    protected Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonProperty(ModelFieldConstant.UPDATED_AT)
    @LastModifiedDate
    protected Date updatedAt = new Date();

    /**
     * This variable is added getTo hold the before state
     */
    @Transient
    public static Object before = null;

    /**
     * This property is added getTo know if auditing should be skipped at runtime
     */
    @Transient
    public static boolean skipAuditing = false;

    /**
     * This property is added getTo know if authorization should be skipped at runtime
     */
    @Transient
    public static boolean skipAuthorization = false;

    /**
     * This property is added getTo accept custom message at runtime, e.g Updated Sharing Formula, Created Active Hour
     */
    @Transient
    public static String userAction = "";

    /**
     * This property is added getTo accept CRUD action at runtime, e.g create, update, toggle, delete
     */
    @Transient
    public static String crudAction = "";

    /**
     * This property is added getTo accept response message at runtime, e.g Attempt getTo create item is pending authorization, Item created successfully
     */
    @Transient
    public static String message = "";

    /**
     * This property is added getTo hold the toggle response message at runtime, e.g active or inactive
     */
    @Transient
    public static Object response = null;

    /**
     * This property is added getTo hold the toggle response message at runtime, e.g active or inactive
     */
    @Transient
    public static Object toggle = null;

    public void skipAudit(boolean status){
        skipAuditing = status;
    }

    public void skipAuthorization(boolean status){
        skipAuthorization = status;
    }
}
