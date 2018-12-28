package com.enenim.scaffold.shared;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IdName {
    private Long id;
    private String name;

    public IdName(Long id, String name){
        this.id = id;
        this.name = name;
    }
}