package com.debug.middleware.server.entity;


import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

@Data
@ToString
public class LoginEvent extends ApplicationEvent implements Serializable {

    private String userName;

    private String loginName;

    private  String ip;


    public LoginEvent(Object source){

        super(source);
    }

    public LoginEvent(Object source,String userName,String loginName,String ip){

        super(source);
        this.userName = userName;
        this.loginName = loginName;
        this.ip = ip;
    }
}
