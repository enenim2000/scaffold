package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "notifications", uniqueConstraints = @UniqueConstraint(columnNames = {"login_id", "_key", "model_type", "model_id", "state"}))
public class Notification extends BaseModel {

    @NotNull
    @OneToOne
    private Login login;

    @JsonProperty("settingKey")
    @NotNull
    @Column(unique = true)
    private String _key;

    @NotNull
    @Column(unique = true)
    private String model_type;

    @NotNull
    @Column(unique = true)
    private String model_id;

    @NotNull
    @Column(unique = true)
    private String state;
    
    private String title;

    private String message;
}