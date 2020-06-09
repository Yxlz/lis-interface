package com.cdxt.app.webservice.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: webservice常量
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/25 17:15
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class WsConst {

    /**
     * webservice名字空间
     */
    public static final String NAMESPACE_URL = "http://webservice.app.cdxt.com/";

    /**
     * 自贡大安区人民医院
     */
    public static final String HOSPITAL_ZIGONGSHI_DAANQU_RENMINYY = "ZiGongShiDaAnQuRenMinYY";

    /**
     * 重庆市渝北区人民医院
     */
    public static final String HOSPITAL_CHONGQINGSHI_YUBEIQU_RENMINYY = "ChongQingShiYuBeiQuRenMinYY";

    /*-----------------------------------------LIS调用的操作代码----------------------------------------------*/

    public static final String ACTION_SEND_REPORT = "SendReport";

    public static final String ACTION_SAMPLE_REFUSE = "SampleRefuse";

    public static final String ACTION_SAMPLE_RECEIVE = "SampleReceive";

    public static final String ACTION_UPDATE_APPLICATION_STATE = "UpdateApplicationState";

    /*-----------------------------------------LIS调用的操作代码----------------------------------------------*/

    /**
     * 存放服务操作代码 key：接口服务代码 value：服务名称
     */
    public static final Map<String, String> ACTION_REQ_MAP = new HashMap<>();

    static {
        ACTION_REQ_MAP.put(ACTION_SEND_REPORT, "JH0421检验报告回传服务");
        ACTION_REQ_MAP.put(ACTION_SAMPLE_RECEIVE, "JH0413检体核收服务");
        ACTION_REQ_MAP.put(ACTION_SAMPLE_REFUSE, "JH0414检体拒收服务");
        ACTION_REQ_MAP.put(ACTION_UPDATE_APPLICATION_STATE, "JH0411检验状态信息更新服务");
    }
}
