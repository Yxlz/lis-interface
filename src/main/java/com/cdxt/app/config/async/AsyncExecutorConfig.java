package com.cdxt.app.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 异步线程池配置类，可配置多个线程池，在调用方法上加注解 @Async("customServiceExecutor")或 @Async("asyncServiceExecutor")
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/27 0027 15:27
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncExecutorConfig implements AsyncConfigurer {

    @Bean
    public ThreadPoolTaskExecutor asyncServiceExecutor(){
        //返回可用处理器的虚拟机的最大数量不小于1
        int cpu = Runtime.getRuntime().availableProcessors();
        log.info("start asyncServiceExecutor cpu : {}", cpu);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(cpu);
        //配置最大线程数 线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(cpu);
        //配置队列大小 缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(500);
        //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(60);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");
        // 使用预定义的异常处理类
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Bean
    public ThreadPoolTaskExecutor customServiceExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor=new VisiableThreadPoolTaskExecutor();
        //线程核心数目
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(10);
        //配置队列大小
        threadPoolTaskExecutor.setQueueCapacity(50);
        //配置线程池前缀
        threadPoolTaskExecutor.setThreadNamePrefix("custom-service-");
        //配置拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //数据初始化
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

//    @Override
//    public Executor getAsyncExecutor() {
//        return asyncServiceExecutor();
//    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            for (Object param : objects) {
                sb.append(param).append(",");
            }
            log.error("Exception message - {}，Method name - {}，Parameter value - {}", throwable.getMessage(), method.getName(), sb.toString());
        };

    }
}
