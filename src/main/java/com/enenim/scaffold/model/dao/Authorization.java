package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.AuthorizationStatus;
import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "authorizations")
public class Authorization extends BaseModel {

    public Authorization(){
    }

    public Authorization(Long id){
        setId(id);
    }

    @OneToOne
    private Task task;

    @OneToOne
    private Staff staff;

    @NotNull
    @Column(length = 10)
    private String action;

    @NotNull
    @Column(length = 40)
    private String rid;

    @NotNull
    @Column(columnDefinition = DataTypeConstant.TEXT)
    private AuthorizationStatus status = AuthorizationStatus.NOT_FORWARDED;

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String comment = "";
/*
    @OneToMany(mappedBy = "authorization", fetch = FetchType.LAZY)
    private Set<Audit> audits = new HashSet<>();*/
}