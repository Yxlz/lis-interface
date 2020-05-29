package com.cdxt.inter.aop;

import com.cdxt.inter.annotation.SwitchDataSource;
import com.cdxt.inter.config.dynamicDS.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Description: 切換數據源切口
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/26 16:08
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Slf4j
@Aspect
@Component
public class SwitchDataSourceAspect implements Ordered {

    @Before("@annotation(switchDataSource)")
    public void switchDataSource(JoinPoint joinPoint, SwitchDataSource switchDataSource){
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info("============"+ classType + "." + methodName + ":设置了读写分离-切换到只读数据库");
        DynamicDataSourceContextHolder.setDataSourceType(DynamicDataSourceContextHolder.DataSourceType.SLAVE);
    }

    @After("@annotation(switchDataSource)")
    public void afterSwitchDataSource(JoinPoint joinPoint, SwitchDataSource switchDataSource){
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
