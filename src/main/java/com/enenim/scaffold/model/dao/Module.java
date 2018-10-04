package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.VisibilityStatus;
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
@Table(name = "modules")
public class Module extends BaseModel {

    @NotNull
    @Column(unique = true, length = 50)
    private String name;

    @NotNull
    @Column(length = 100)
    private String description;

    @NotNull
    private VisibilityStatus visibility = VisibilityStatus.YES;

    @JsonProperty("order")
    @NotNull
    @Column(length = 11)
    private Integer _order;

    @NotNull
    @Column(length = 20)
    private String icon;

    @JsonBackReference
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    private Set<Task> tasks = new HashSet<>();
}