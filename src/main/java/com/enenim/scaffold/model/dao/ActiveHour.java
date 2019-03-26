package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "active_hours" , uniqueConstraints = @UniqueConstraint(columnNames={"beginTime", "endTime"}))
@ToString
public class ActiveHour extends BaseModel {
    @NotNull
    @Column(unique = true, length = 100)
    private String name;

    @NotNull
    @JsonProperty("begin_time")
    private Time beginTime;

    @NotNull
    @JsonProperty("end_time")
    private Time endTime;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @JsonBackReference
    @OneToMany(mappedBy = "activeHour", fetch = FetchType.LAZY)
    @ApiModelProperty(required = true, hidden = true)
    private Set<Staff> staff = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "activeHour", fetch = FetchType.LAZY)
    @ApiModelProperty(required = true, hidden = true)
    private Set<Group> groups = new HashSet<>();

    public ActiveHour(){}

    public ActiveHour(Long id) {
        super();
        this.id = id;
    }
}