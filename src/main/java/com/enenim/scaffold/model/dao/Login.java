package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.LoginStatus;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "logins")
@ToString
public class Login extends BaseModel {

    @NotNull
    @Column(unique = true, length = 70)
    private String username;

    @JsonIgnore
    @Column
    private String password;

    @NotNull
    @Column(length = 50)
    @JsonProperty("user_type")
    private String userType;

    @NotNull
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("last_logged_in")
    private Date lastLoggedIn;

    @NotNull
    private LoginStatus status = LoginStatus.ENABLED;

    @NotNull
    private VerifyStatus verifyStatus = VerifyStatus.NOT_VERIFIED;

    @OneToOne(mappedBy = "login", fetch = FetchType.LAZY)
    @ApiModelProperty(required = true, hidden = true)
    private SecretQuestion secretQuestion;

    @JsonBackReference
    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    @ApiModelProperty(required = true, hidden = true)
    private Set<Audit> audits = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    @ApiModelProperty(required = true, hidden = true)
    private Set<Notification> notifications  = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    @ApiModelProperty(required = true, hidden = true)
    private Set<PasswordReset> passwordResets = new HashSet<>();

    /*@JsonBackReference
    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    @ApiModelProperty(required = true, hidden = true)
    private Set<Tracker> trackers = new HashSet<>();*/
}
