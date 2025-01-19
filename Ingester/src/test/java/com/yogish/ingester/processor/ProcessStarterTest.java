package com.yogish.ingester.processor;

import io.camunda.operate.exception.OperateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProcessStarterTest {


    @Autowired
    ProcessStarter starter;
    @Test
    void startProcessInstance() {


        HashMap<String, Object> map = new HashMap<>();
        map.put("name","yogish");
        starter.startProcessInstance("Process_1yvmqaq",map);
    }


    @Test
    void u() throws OperateException {
        starter.getallWorkFLows();
    }
}