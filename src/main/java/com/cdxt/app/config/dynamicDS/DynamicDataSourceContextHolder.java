package com.cdxt.app.config.dynamicDS;

/**
 * @Description: 动态数据源上下文管理
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/23 0023 17:37
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class DynamicDataSourceContextHolder {

    public enum DataSourceType{
        MASTER,SLAVE
    }
    /**
     * 存放当前线程使用的数据源类型
     */
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    /**
     * @return:  void
     * @author: tangxiaohui
     * @description: 设置数据源
     * @Param dataSourceType:  数据源类型
     * @date: 2020/5/23 0023 17:44
     */
    public static void setDataSourceType(DataSourceType dataSourceType){
        contextHolder.set(dataSourceType);
    }

    /**
     * @return:  com.yx.app.db.DynamicDataSourceContextHolder.DataSourceType
     * @author: tangxiaohui
     * @description: 获取数据源
     * @date: 2020/5/23 0023 17:46
     */
    public static DataSourceType getDataSourceType(){
        return contextHolder.get() == null ? DataSourceType.MASTER : contextHolder.get();
    }

    /**
     * @return:  void
     * @author: tangxiaohui
     * @description: 清除数据源
     * @date: 2020/5/23 0023 17:47
     */
    public static void clearDataSourceType(){
        contextHolder.remove();
    }
}
