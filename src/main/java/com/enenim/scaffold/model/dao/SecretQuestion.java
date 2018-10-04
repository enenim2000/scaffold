package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "secret_questions")
public class SecretQuestion extends BaseModel {

    @NotNull
    @OneToOne
    private Login login;

    @NotNull
    private String question;

    @NotNull
    private String answer;
}
