package com.debug.middleware.server.entity;

import lombok.Data;
import lombok.ToString;
import sun.text.bidi.BidiLine;

@Data
@ToString
public class Person {

    private Integer id;

    private String name;

    private String userName;

    public Person(Integer id,String name,String userName){

        this.id = id;
        this.name = name;
        this.userName = userName;
    }
}
