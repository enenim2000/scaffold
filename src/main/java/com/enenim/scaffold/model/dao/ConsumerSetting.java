package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "consumer_settings")
public class ConsumerSetting extends BaseModel {

    @JsonProperty("settingKey")
    @Column(length = 40)
    private String _key;

    @JsonProperty("value")
    private String _value;

    @ManyToOne
    private Consumer consumer;
}