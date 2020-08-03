package com.debug.middleware.server.service;

import com.debug.middleware.model.entity.dto.RedPacketDto;

import java.util.List;

public interface IRedService {

    void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception;
}
