package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "public_holidays", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "holiday"}))
public class PublicHoliday extends BaseModel {

    @NotNull
    @Column(unique = true)
    private Date date;

    @NotNull
    @Column(length = 70)
    private String holiday;

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String comment;

    @JsonBackReference
    @ManyToMany(mappedBy = "publicHolidays")
    @ApiModelProperty(required = true, hidden = true)
    private Set<Branch> branches = new HashSet<>();
}