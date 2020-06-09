package com.cdxt.app.model.response;

import com.cdxt.app.annotation.Path;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 检验申请单响应
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/31 0031 9:08
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class InspecReqResponse {
    /**
     * 消息流水号Id
     */
    @Path(path = "id", attribute = "extension")
    private String messageId;

    /**
     * 消息创建时间
     */
    @Path(path = "creationTime", attribute = "value", dateformat = "yyyyMMddHHmmss")
    private Date creationTime;

    /**
     * 服务标识,extension为唯一事件编码，区分服务事件
     */
    @Path(path = "interactionId", attribute = "extension")
    private String serviceCode;

    /**
     * 处理代码，标识此消息是否是产品、训练、调试系统的一部分。D：调试；P：产品；T：训练
     */
    @Path(path = "processingCode", attribute = "code")
    private String processingCode;

    /**
     * 消息处理模式: 取值可以为以下列出值中任意一个，正式使用时设置为"T" A(Archive);I(Initial load); R(Restore from archive); T(Current processing)
     */
    @Path(path = "processingModeCode", attribute = "code")
    private String processingModeCode;

    /**
     * 消息接收确认类型
     */
    @Path(path = "acceptAckCode", attribute = "code")
    private String acceptAckCode;

    /**
     * 消息接收者ID
     */
    @Path(path = "receiver/device/id", attribute = "extension")
    private String receiverId;

    /**
     * 消息发送者ID
     */
    @Path(path = "sender/device/id", attribute = "extension")
    private String senderId;

    /**
     * 响应代码    AA成功  AE失败
     */
    @Path(path = "acknowledgement", attribute = "typeCode")
    private String responseCode;

    /**
     * 请求消息ID
     */
    @Path(path = "acknowledgement/targetMessage/id", attribute = "extension")
    private String sourceId;

    /**
     * 响应内容
     */
    @Path(path = "acknowledgement/acknowledgementDetail/text")
    private String responseMessage;
}
