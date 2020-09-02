package com.debug.middleware.server.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventInfo implements Serializable {


    private Integer id;
    private String module;
    private String name;
    private String desc;

    public EventInfo(){

    }

    public EventInfo(Integer id,String module,String name,String desc){

        this.id = id;
        this.module = module;
        this.name = name;
        this.desc = desc;

    }
}
