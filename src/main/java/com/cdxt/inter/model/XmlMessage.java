package com.cdxt.inter.model;

import com.cdxt.inter.annotation.Path;
import lombok.Data;

import java.util.Date;

/**
 * <R> 请求消息模型
 * @Description: XML消息
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 11:34
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class XmlMessage<R> {
    /**
     * 消息流水号Id
     */
    @Path(path="id", attribute="extension")
    private String messageId;

    /**
     * 消息创建时间
     */
    @Path(path="creationTime", attribute="value",dateformat="yyyyMMddHHmmss")
    private Date creationTime;

    /**
     * 服务标识,extension为唯一事件编码，区分服务事件
     */
    @Path(path="interactionId", attribute="extension")
    private String serviceCode;

    /**
     * 处理代码，标识此消息是否是产品、训练、调试系统的一部分。D：调试；P：产品；T：训练
     */
    @Path(path="processingCode", attribute="code")
    private String processingCode;

    /**
     * 消息处理模式: 取值可以为以下列出值中任意一个，正式使用时设置为"T" A(Archive);I(Initial load); R(Restore from archive); T(Current processing)
     */
    @Path(path="processingModeCode", attribute="code")
    private String processingModeCode;

    /**
     * 消息接收确认类型
     */
    @Path(path="acceptAckCode", attribute="code")
    private String acceptAckCode;

    /**
     * 消息发送者ID
     */
    @Path(path="sender/device/id/item", attribute="extension")
    private String senderId;

    /**
     * 消息接收者ID
     */
    @Path(path="receiver/device/id/item", attribute="extension")
    private String receiverId;

    private R subject;
}
