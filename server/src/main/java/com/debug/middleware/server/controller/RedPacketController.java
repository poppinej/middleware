package com.debug.middleware.server.controller;


import com.debug.middleware.model.entity.dto.RedPacketDto;
import com.debug.middleware.server.service.IRedPacketService;
import enums.BaseResponse;
import enums.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/redPacket")
public class RedPacketController {

    private static final Logger log = LoggerFactory.getLogger(RedPacketController.class);

    @Autowired
    private IRedPacketService iRedPacketService;

    @RequestMapping(value = "/handOut")
    public Object handOut(@RequestBody  RedPacketDto dto){

        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);

        try{
            String redId = iRedPacketService.handOut(dto);

            baseResponse.setData(redId);

        }catch (Exception ex){

            log.error("发红包发生异常"+ex.fillInStackTrace());
            baseResponse = new BaseResponse(StatusCode.Fail);
        }


        return baseResponse;



    }
}
