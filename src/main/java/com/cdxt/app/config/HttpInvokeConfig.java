package com.cdxt.app.config;

import app.manager.api.LisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

/**
 * @Description: httpinvoke调用lisservice配置类
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/28 17:02
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Configuration
public class HttpInvokeConfig {

    @Value("${lisServerAddr}")
    private String lisServiceAddr;

    @Bean
    public HttpInvokerProxyFactoryBean lisServiceBean(){
        HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
        factoryBean.setServiceUrl(lisServiceAddr);
        factoryBean.setServiceInterface(LisService.class);
        factoryBean.setHttpInvokerRequestExecutor(httpComponentsHttpInvokerRequestExecutor());
        return factoryBean;
    }

    @Bean
    public HttpComponentsHttpInvokerRequestExecutor httpComponentsHttpInvokerRequestExecutor(){
        return new HttpComponentsHttpInvokerRequestExecutor();
    }
}
