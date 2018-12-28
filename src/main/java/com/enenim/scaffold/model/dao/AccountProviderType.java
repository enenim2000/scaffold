package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.interfaces.IAuthorization;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "account_provider_types")
public class AccountProviderType extends BaseModel implements IAuthorization {

    public AccountProviderType(){}

    public AccountProviderType(Long id){
        this.id = id;
    }

    @Transient
    public static String searchables = "name";

    @NotNull
    @Column(unique=true)
    private String name;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @JsonBackReference
    @JsonProperty("account_providers")
    @OneToMany(mappedBy = "accountProviderType", fetch = FetchType.LAZY)
    private Set<AccountProvider> accountProviders = new HashSet<>();
}