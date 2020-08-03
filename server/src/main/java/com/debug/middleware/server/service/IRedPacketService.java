package com.debug.middleware.server.service;

import com.debug.middleware.model.entity.dto.RedPacketDto;

public interface IRedPacketService {

    String handOut(RedPacketDto dto) throws Exception;
}
