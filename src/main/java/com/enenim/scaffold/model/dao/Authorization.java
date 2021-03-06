package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.AuthorizationStatus;
import com.enenim.scaffold.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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
    @Column(unique = true)
    private String rid;

    @NotNull
    private AuthorizationStatus status = AuthorizationStatus.NOT_FORWARDED;

    private String comment = "";

    @OneToMany(mappedBy = "authorization", fetch = FetchType.LAZY)
    private Set<Audit> audits = new HashSet<>();
}