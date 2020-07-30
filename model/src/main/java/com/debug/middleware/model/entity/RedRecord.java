package com.debug.middleware.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RedRecord {
    private Integer id;

    private Integer userId;

    private String redPacket;

    private Integer total;

    private BigDecimal amount;

    private Byte isActive;

    private Date createTime;


}