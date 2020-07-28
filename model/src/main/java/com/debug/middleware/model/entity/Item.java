package com.debug.middleware.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

    @Data
    public class Item {

        private Integer id;

        private String code;

        private String name;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date createTime;
    }


