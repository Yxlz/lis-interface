package com.cdxt.inter.config;

import com.cdxt.inter.config.dynamicDS.DynamicDataSource;
import com.cdxt.inter.config.dynamicDS.DynamicDataSourceContextHolder;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 数据源配置类
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/23 0023 17:53
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.cdxt.inter.dao"}, sqlSessionFactoryRef = "dynamicSqlSessionFactory")
public class DataSourceMybatisConfig {

    /**
     * @return:  javax.sql.DataSource
     * @author: tangxiaohui
     * @description: 主数据源
     * @date: 2020/5/23 0023 17:57
     */
    @Bean(name = "dataSourceMaster")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public HikariDataSource dataSourceMaster(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * @return:  javax.sql.DataSource
     * @author: tangxiaohui
     * @description: 从数据源
     * @date: 2020/5/23 0023 17:57
     */
    @Bean(name = "dataSourceSlave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public HikariDataSource dataSourceSlave(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * @return:  javax.sql.DataSource
     * @author: tangxiaohui
     * @description: 动态数据源，通过AOP动态切换
     * @date: 2020/5/23 0023 18:03
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置默认主数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSourceMaster());
        //配置多数据源
        Map<Object,Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DynamicDataSourceContextHolder.DataSourceType.MASTER,dataSourceMaster());
        dataSourceMap.put(DynamicDataSourceContextHolder.DataSourceType.SLAVE,dataSourceSlave());
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    /**
     * @return:  org.apache.ibatis.session.SqlSessionFactory
     * @author: tangxiaohui
     * @description: 配置sqlSessionFactory
     * @date: 2020/5/23 0023 19:34
     */
    @Bean(name = "dynamicSqlSessionFactory")
    public SqlSessionFactory dynamicSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:com/cdxt/inter/dao/*/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "dynamicTransactionManager")
    public DataSourceTransactionManager dynamicTransactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
