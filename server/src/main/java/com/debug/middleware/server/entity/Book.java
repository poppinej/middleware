package com.debug.middleware.server.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {

    public Book(Integer bookNo, String name){

        this.setName(name);
        this.setBookNo(bookNo);

    }

   public Book(){}


    private Integer bookNo;
    private String name;
}
