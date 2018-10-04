package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.HolidayLogin;
import com.enenim.scaffold.enums.WeekendLogin;
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
@Table(name = "groups")
public class Group extends BaseModel {

    public Group() {
    }

    public Group(Long id) {
        super();
        this.setId(id);
    }

    @NotNull
    @Column(unique = true, length = 40)
    private String name;

    @NotNull
    @Column(unique = true, length = 40)
    private String role;

    @NotNull
    @OneToOne
    private ActiveHour activeHour;

    @NotNull
    @JsonProperty("holiday_login")
    private HolidayLogin holidayLogin;

    @NotNull
    @JsonProperty("weekend_login")
    private WeekendLogin weekendLogin;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @JsonBackReference
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<Staff> staff = new HashSet<>();

    @JsonBackReference
    @ManyToMany()
    @JoinTable(name = "permission",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "task_id"})
    )
    private Set<Task> tasks = new HashSet<>();

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)

    @JoinTable(name = "permission_authorizer",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "task_id"})
    )
    private Set<Task> authorizerTasks = new HashSet<>();


    public void addTask(Task task) {
        authorizerTasks.add(task);
        task.getGroups().add(this);
    }

    
    public void removeTask(Task task) {
        authorizerTasks.remove(task);
        task.getGroups().remove(this);
    }
}
