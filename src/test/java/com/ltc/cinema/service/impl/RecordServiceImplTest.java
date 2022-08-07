package org.ltc.cinema.service.impl;

import org.ltc.cinema.service.RecordService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zrk
 * @version 1.0
 * @date 2020/5/1 0001 12:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class RecordServiceImplTest {
@Autowired
    RecordService recordService;
    @Test
    void getSchart1Data() {
        System.out.println(recordService.getSchart1Data());
    }
}