package com.cdxt.app.config.dynamicDS;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
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
@MapperScan(basePackages = {"com.cdxt.app.dao"}, sqlSessionFactoryRef = "dynamicSqlSessionFactory")
public class DataSourceMybatisConfig {

    /**
     * @return: javax.sql.DataSource
     * @author: tangxiaohui
     * @description: 主数据源
     * @date: 2020/5/23 0023 17:57
     */
    @Bean(name = "dataSourceMaster")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public HikariDataSource dataSourceMaster() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * @return: javax.sql.DataSource
     * @author: tangxiaohui
     * @description: 从数据源
     * @date: 2020/5/23 0023 17:57
     */
    @Bean(name = "dataSourceSlave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public HikariDataSource dataSourceSlave() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * @return: javax.sql.DataSource
     * @author: tangxiaohui
     * @description: 动态数据源，通过AOP动态切换
     * @date: 2020/5/23 0023 18:03
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置默认主数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSourceMaster());
        //配置多数据源
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DynamicDataSourceContextHolder.DataSourceType.MASTER, dataSourceMaster());
        dataSourceMap.put(DynamicDataSourceContextHolder.DataSourceType.SLAVE, dataSourceSlave());
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

//    /**
//     * @return: org.apache.ibatis.session.SqlSessionFactory
//     * @author: tangxiaohui
//     * @description: Mybatis 配置sqlSessionFactory
//     * @date: 2020/5/23 0023 19:34
//     */
//    @Bean(name = "dynamicSqlSessionFactory")
//    public SqlSessionFactory dynamicSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dynamicDataSource);
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        bean.setMapperLocations(resolver.getResources("classpath:com/cdxt/app/dao/*/*.xml"));
//        return bean.getObject();
//    }

    /**
     * @return:  com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean
     * @author: tangxiaohui
     * @description: Mybatis Plus配置 sqlsessionfactory
     * @Param dynamicDataSource:
     * @date: 2020/6/24 10:38
     */
    @Bean(name = "dynamicSqlSessionFactory")
    public MybatisSqlSessionFactoryBean sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/cdxt/app/dao/*/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean(name = "dynamicTransactionManager")
    public DataSourceTransactionManager dynamicTransactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }


    /**
     * @return: com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     * @description: 分页插件
     * @date: 2020/6/20 0020 19:01
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
