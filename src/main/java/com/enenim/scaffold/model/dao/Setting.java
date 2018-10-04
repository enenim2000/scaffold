package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "settings")
public class Setting extends BaseModel{
}
