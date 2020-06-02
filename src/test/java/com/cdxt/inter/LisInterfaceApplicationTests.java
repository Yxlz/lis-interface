package com.cdxt.inter;

import app.manager.api.LisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LisInterfaceApplicationTests {

    @Autowired
    private LisService service;

    @Test
    void contextLoads() {
//        List<LisInspecUsers> users = service.getAllLisInspecUsers();
//        for (int i = 0; i < users.size(); i++) {
//            System.out.println(users.get(i).getUsername());
//        }
    }

}
