package com.debug.middleware.server.service.impl;

import com.debug.middleware.model.entity.dto.RedPacketDto;
import com.debug.middleware.server.service.IRedPacketService;
import com.debug.middleware.server.service.IRedService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import util.RedPacketUtil;

import java.util.List;

@Service
public class IRedPacketServiceImpl implements IRedPacketService {


    private static  final Logger logger = LoggerFactory.getLogger(IRedPacketServiceImpl.class);

    private static  final String keyPrefix = "redis:red:packet:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IRedService iRedService;

    @Override
    public String handOut(RedPacketDto dto) throws Exception {

        if(dto.getAmount() > 0 && dto.getTotal() >0){

            List<Integer> list  = RedPacketUtil.divideRedPackage(dto.getAmount(),dto.getTotal());

            String timestamp = String.valueOf(System.nanoTime());

            String redId = keyPrefix + dto.getUserId() + timestamp;
            redisTemplate.opsForList().leftPushAll(redId,list);

            String redTotalKey = redId + ":total";
            redisTemplate.opsForValue().set(redTotalKey,dto.getTotal());

            iRedService.recordRedPacket(dto,redId,list);

            return  redId;
        }else {
            throw new Exception("系统异常：参数错误！");
        }
    }
}
