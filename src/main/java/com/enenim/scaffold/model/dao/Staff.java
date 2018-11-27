package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.HolidayLogin;
import com.enenim.scaffold.enums.WeekendLogin;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "staff")
public class Staff extends BaseModel{

    public Staff() {
    }

    public Staff(Long id) {
        super();
        this.setId(id);
    }

    @NotNull
    @ManyToOne
    private Branch branch = null;

    @NotNull
    @ManyToOne
    private Group group = null;

    @NotNull
    @Column(unique = true, length = 20)
    @JsonProperty("employee_id")
    private String employeeId = null;

    @ManyToOne
    private ActiveHour activeHour = null;

    @NotNull
    @Column(length = 60)
    @JsonProperty("fullname")
    private String fullName = null;

    @NotNull
    @Column(unique = true, length = 100)
    private String email;

    @JsonProperty("holiday_login")
    private HolidayLogin holidayLogin = null;

    @JsonProperty("weekend_login")
    private WeekendLogin weekendLogin = null;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    /*@JsonBackReference
    @OneToOne(mappedBy = "staff", fetch = FetchType.LAZY)
    private TillProfile tillProfile;

    @JsonBackReference
    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();*/
}
