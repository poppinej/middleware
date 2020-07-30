package com.debug.middleware.server;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.RedPacketUtil;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedPackageTest {

    private static final Logger log = LoggerFactory.getLogger(RedPackageTest.class);


    @Test
    public void divideRedPackage(){

        Integer amount = 1000;

        Integer total = 10;

        List<Integer> amountList = RedPacketUtil.divideRedPackage(amount,total);

        System.out.println(amountList);
    }
}
