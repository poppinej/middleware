package com.debug.middleware.server.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Data
@ToString
public class PhoneUser implements Serializable {

    private String phone;
    private Double fare;

    public PhoneUser(){

    }

    public PhoneUser(String phone,Double fare){

        this.fare = fare;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneUser phoneUser = (PhoneUser) o;
        return Objects.equals(phone, phoneUser.phone);
    }

}
