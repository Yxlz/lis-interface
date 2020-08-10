package com.cdxt.app.model.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
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
@JacksonXmlRootElement(localName = "request")
public class LisRequestionXml {

    @JacksonXmlProperty(localName = "inputDate")
    private String inputDate;
    @JacksonXmlProperty(localName = "devCode")
    private String devCode;
    @JacksonXmlProperty(localName = "inspecNo")
    private String inspecNo;
    @JacksonXmlProperty(localName = "barCode")
    private String barCode;
    @JacksonXmlProperty(localName = "hospid")
    private String hospid;
}
