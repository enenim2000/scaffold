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
@Table(name = "vendor_settings", uniqueConstraints = @UniqueConstraint(columnNames = {"vendor_id", "_key"}))
public class VendorSetting extends BaseModel {

    @NotNull
    @Column(length = 50, name = "_category")
    @JsonProperty("category_key")
    private String categoryKey;

    @NotNull
    @Column(name = "_key")
    @JsonProperty("setting_key")
    private String settingKey;

    @NotNull
    @Column(name = "_value")
    private String value;

    @ManyToOne
    private Vendor vendor;
}