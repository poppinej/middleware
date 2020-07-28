package com.debug.middleware.server.controller;


import com.debug.middleware.server.service.CachePassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/cache")
public class CachePassController {


    @Autowired
    private CachePassService cachePassService;

    @RequestMapping(value = "/getItem")
    public Map<String,Object> getItem(String item){

        Map<String,Object> res = new HashMap<>(8);

        res.put("code",0);
        res.put("msg","牛逼");

        try{

            res.put("data",cachePassService.getItemInfo(item));

        }catch (Exception e){
            res.put("code",-1);
            res.put("msg","傻逼"+e.getMessage());
            e.printStackTrace();
        }

        return res;
    }




}
