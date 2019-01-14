package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.interfaces.IAuthorization;
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
@Table(name = "branches")
public class Branch extends BaseModel implements IAuthorization {


    public Branch() {
    }

    public Branch(Long id) {
        super();
        this.setId(id);
    }

    @NotNull
    @Column(unique = true, length = 50)
    private String sol;

    @NotNull
    @Column(length = 200)
    private String name;

    @NotNull
    @Column(length = 200)
    private String address;

    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "branch_public_holiday",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "public_holiday_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"branch_id", "public_holiday_id"})
    )
    private Set<PublicHoliday> publicHolidays = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private Set<Staff> staff = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();
}