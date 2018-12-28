package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.DiscountType;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "promotions", uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "name"}))
public class Promotion extends BaseModel {

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String description = "";

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String terms = "";

    @NotNull
    @JsonProperty("number_of_cycles")
    private Integer numberOfCycles;

    @JsonProperty("discount_type")
    @Convert(converter = DiscountType.Converter.class)
    private DiscountType discountType;

    @NotNull
    @JsonProperty("discount_value")
    private Double discountValue;

    @NotNull
    @Column(unique = true, length = 50)
    private String slug;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @NotNull
    @ManyToOne
    private Item item;
}
