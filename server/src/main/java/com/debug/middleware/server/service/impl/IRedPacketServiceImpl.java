package com.debug.middleware.server.service.impl;

import com.debug.middleware.model.entity.dto.RedPacketDto;
import com.debug.middleware.server.service.IRedPacketService;
import com.debug.middleware.server.service.IRedService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import util.RedPacketUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Override
    public BigDecimal rob(Integer userId, String redId) throws Exception {

        ValueOperations valueOperations = redisTemplate.opsForValue();

        Object obj = valueOperations.get(redId+userId+"rob");
        if(obj != null){
            return new BigDecimal(obj.toString());
        }

        Boolean res = click(redId);
        if(res) {


            //以免高并发情况下出现一个用户抢多个红包，添加分布式锁
            final String lockKey = redId + userId + "-lock";
            Boolean lock = valueOperations.setIfAbsent(lockKey, redId);
            redisTemplate.expire(lockKey, 24L, TimeUnit.HOURS);

            try {
                if (lock) {

                    Object val = redisTemplate.opsForList().rightPop(redId);
                    String redTotal = redId + ":total";
                    Object total = valueOperations.get(redTotal);
                    Integer curTotal = total != null ? Integer.parseInt(total.toString()) - 1 : 0;
                    valueOperations.set(redId + ":total", curTotal);

                    BigDecimal result = new BigDecimal(val.toString()).divide(new BigDecimal(100));

                    iRedService.robRedPacket(userId, redId, new BigDecimal(val.toString()));
                    valueOperations.set(redId + userId + "rob", result, 24L, TimeUnit.HOURS);

                    logger.info("牛逼，你居然抢到红包了！");

                    return result;

                }
            } catch (Exception e) {
                throw new Exception("分布式锁失效！");
            }
        }

        return null;

    }


    private Boolean click(String redId){

        ValueOperations valueOperations = redisTemplate.opsForValue();

        Object obj = valueOperations.get(redId + ":total");

        if(obj != null && Integer.parseInt(obj.toString() )> 0){

            return true;
        }else {
            return false;
        }
    }
}
