package com.cdxt.inter.model.request.mults;

import com.cdxt.inter.annotation.Path;
import com.cdxt.inter.constants.DocConstants;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 检验报告XML中的检验执行项
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/3 14:38
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class InspecItemExcute {
    @Path(path = "act/code", attribute = "code")
    private String inspecItemCode;//检验项目代码，代表具体的检验项目

    @Path(path = "act/code", attribute = "displayName")
    private String inspecItemName;//检验项目

    @Path(path = "act/effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date checkTime;//审核时间

    @Path(path = "act/performer/assignedEntity/id", attribute = "extension")
    private String inspecDoctorJobNo;//检查执行者工号

    @Path(path = "act/performer/assignedEntity/assignedPerson/name")
    private String inspecDoctorName;//检查执行者姓名

    @Path(path = "act/participant/participantRole/id", attribute = "extension")
    private String devCode;//设备号

    @Path(path = "act/participant/participantRole/playingDevice/manufacturerModelName")
    private String devName;//设备名称

    @Path(path = "act/entryRelationship/procedure/methodCode", attribute = "code")
    private String inspecMethodCode;//检验方法代码

    @Path(path = "act/entryRelationship/procedure/methodCode", attribute = "displayName")
    private String inspecMethodName;//检验方法
}
