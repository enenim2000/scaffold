package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile extends BaseModel {

    public Profile() {
    }

    public Profile(Long id) {
        super();
        this.setId(id);
    }

    @NotNull
    @Column(unique = true, length = 100)
    private String name;

    @NotNull
    @Column(length = 30)
    private String type;

    @NotNull
    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String data = "";

    @JsonBackReference
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private Set<TransactionCharge> transactionCharges = new HashSet<>();

}