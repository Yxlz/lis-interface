package com.cdxt.app;

import app.manager.api.LisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootTest
class LisInterfaceApplicationTests {

    @Autowired
    private LisService service;

    @Qualifier("customServiceExecutor")
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Test
    void contextLoads() {
//        List<LisInspecUsers> users = service.getAllLisInspecUsers();
//        for (int i = 0; i < users.size(); i++) {
//            System.out.println(users.get(i).getUsername());
//        }
        //CompletableFuture<Void> userFuture = CompletableFuture.runAsync(() ->  System.out.println(111), threadPoolTaskExecutor);

    }

    @Test
    void testAsyncService() throws InterruptedException {
//        CountDownLatch countDownLatch = new CountDownLatch(3);
//        System.out.println("sssssssssssssssssssssssss");
//        asyncService.executeAsync(countDownLatch);
//        asyncService.executeAsync(countDownLatch);
//        asyncService.executeAsync(countDownLatch);
//        countDownLatch.await();
//        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeee");
    }
}
