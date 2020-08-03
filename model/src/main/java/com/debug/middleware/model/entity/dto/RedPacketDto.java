package com.debug.middleware.model.entity.dto;


import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RedPacketDto {



    private Integer userId;
    @NotNull
    private Integer total;
    @NotNull
    private Integer amount;
}
