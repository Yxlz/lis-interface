package com.cdxt.inter.model.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Description: webservice接口参数message
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/26 17:51
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
@XStreamAlias("request")
public class LisRequestionXml {

    @XStreamAlias("inputDate")
    private String inputDate;
    @XStreamAlias("devCode")
    private String devCode;
    @XStreamAlias("inspecNo")
    private String inspecNo;
    @XStreamAlias("barCode")
    private String barCode;
    @XStreamAlias("hospid")
    private String hospid;
}
