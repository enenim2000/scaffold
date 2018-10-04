package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.interfaces.IAudit;
import com.enenim.scaffold.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
@Table(name = "security_questions")
public class SecurityQuestion extends BaseModel implements IAudit {

    @NotNull
    @Column(unique = true)
    private String question;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;
}
