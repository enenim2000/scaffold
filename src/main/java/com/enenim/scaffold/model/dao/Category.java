package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.CategoryGroup;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends BaseModel {

    @NotNull
    @Column(unique=true, length = 50)
    private String name;

    @NotNull
    @Column(length = 40)
    private String icon = "fa fa-circle";

    @Column(length = 100)
    @JsonProperty("custom_url")
    private String customUrl;

    @NotNull
    @Column(length = 11)
    private int rank = 0;

    @Column(length = 11)
    @JsonProperty("featured_rank")
    private String featuredRank;

    @JsonProperty("group")
    @NotNull
    private CategoryGroup _group;

    @NotNull
    @Column(unique = true, length = 50)
    private String slug;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    /*@JsonBackReference
    @ManyToMany
    @JoinTable(name = "category_service",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "service_id"})
    )
    private Set<Service> services = new HashSet<>();*/

    /*@JsonBackReference
    @ManyToMany(mappedBy = "categories")
    private Set<Vendor> vendors = new HashSet<>();*/
}