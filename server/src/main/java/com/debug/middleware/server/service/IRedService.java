package com.debug.middleware.server.service;

import com.debug.middleware.model.entity.dto.RedPacketDto;

import java.math.BigDecimal;
import java.util.List;

public interface IRedService {

    void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception;

    void robRedPacket(Integer userId, String redId, BigDecimal amount);
}
