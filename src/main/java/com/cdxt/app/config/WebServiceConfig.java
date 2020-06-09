package com.cdxt.app.config;

import com.cdxt.app.webservice.LabInfoService;
import com.cdxt.app.webservice.impl.LabInfoServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @Description: cxfwebservice配置类
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/21 14:04
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Configuration
public class WebServiceConfig {

    /**
     * @return:  org.springframework.boot.web.servlet.ServletRegistrationBean
     * @author: tangxiaohui
     * @description: 注入servlet bean name不能是dispatcherServlet 否则会覆盖dispatcherServlet
     * @date: 2020/5/21 14:09
     */
    @Bean(name = "cxfServletRegistration")
    public ServletRegistrationBean dispatcherServlet(){
        //注册servlet 拦截/ws 开头的请求 不设置 默认为：/services/*
        return new ServletRegistrationBean(new CXFServlet(),"/ws/*");
    }

    @Bean(name = "labInfoService")
    public LabInfoService labInfoService(){
        return new LabInfoServiceImpl();
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * @return:  javax.xml.ws.Endpoint
     * @author: tangxiaohui
     * @description: 发布webservice服务
     * @date: 2020/5/21 14:52
     */
    @Bean
    public Endpoint endpoint(LabInfoService labInfoService){
        EndpointImpl endpoint = new EndpointImpl(springBus(),labInfoService);//绑定要发布的服务
        endpoint.publish("/labInfo");//发布地址
        return endpoint;
    }
}
