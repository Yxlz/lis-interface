package com.cdxt.app;

import com.cdxt.app.util.InvokeWebserviceUtil;
import com.cdxt.app.webservice.LabInfoService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CxfClient
 * @Description: 调用webservice的几种方式
 * @Author tangxiaohui
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @DateTime 2020/5/26 15:44
 */
public class CxfClient {
    public static void main(String[] args) {
        //cl3();
        String wsdlUrl = "http://localhost:8080/ws/labInfo?wsdl";
        String namespaceURI = "http://webservice.inter.cdxt.com/";
        String localPart = "InspectionRequisition";
        //String localPart = "Specimen";
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("requisitionXml","<POOR_IN200901UV ITSVersion=\"XML_1.0\" xmlns=\"urn:hl7-org:v3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:hl7-org:v3 ../../Schemas/POOR_IN200901UV.xsd\">\n" +
                "\t<!-- 消息ID-->\n" +
                "\t<id extension=\"121212121212\"/>\n" +
                "\t<!-- 消息创建时间 -->\n" +
                "\t<creationTime value=\"20150701000000\"/>\n" +
                "\t<!-- 服务标识,extension为唯一事件编码，区分服务事件 -->\n" +
                "\t<interactionId root=\"\" extension=\"JH0206\"/>\n" +
                "\t<!-- 消息用途: 取值可以为以下列出值中任意一个,正式使用时设置为\"P\" P(Production); D(Debugging); T(Training)-->\n" +
                "\t<processingCode code=\"P\"/>\n" +
                "\t<!-- 消息处理模式: 取值可以为以下列出值中任意一个，正式使用时设置为\"T\" A(Archive);I(Initial load); R(Restore from archive); T(Current processing)-->\n" +
                "\t<processingModeCode code=\"R\"/>\n" +
                "\t<!-- 消息应答: AL(Always); ER(Error/reject only); NE(Never) -->\n" +
                "\t<acceptAckCode code=\"AL\"/>\n" +
                "\t<!-- 接受者 -->\n" +
                "\t<receiver typeCode=\"RCV\">\n" +
                "\t\t<device classCode=\"DEV\" determinerCode=\"INSTANCE\">\n" +
                "\t\t\t<!-- 接收方ID-->\n" +
                "\t\t\t<id>\n" +
                "\t\t\t\t<item root=\"2.16.156.10011.0.1.1\" extension=\"LIS\"/>\n" +
                "\t\t\t</id>\n" +
                "\t\t</device>\n" +
                "\t</receiver>\n" +
                "\t<!-- 发送者 -->\n" +
                "\t<sender typeCode=\"SND\">\n" +
                "\t\t<device classCode=\"DEV\" determinerCode=\"INSTANCE\">\n" +
                "\t\t\t<!-- 发送方ID-->\n" +
                "\t\t\t<id>\n" +
                "\t\t\t\t<item root=\"2.16.156.10011.0.1.2\" extension=\"EMR\"/>\n" +
                "\t\t\t</id>\n" +
                "\t\t</device>\n" +
                "\t</sender>\n" +
                "\t<controlActProcess classCode=\"CACT\" moodCode=\"EVN\">\n" +
                "\t\t<subject typeCode=\"SUBJ\">\n" +
                "\t\t\t<observationRequest classCode=\"OBS\" moodCode=\"RQO\">\n" +
                "\t\t\t\t<id>\n" +
                "\t\t\t\t\t<!--电子申请单编号@extension-->\n" +
                "\t\t\t\t\t<item root=\"2.16.156.10011.1.24\" extension=\"@8741795311\"/>\n" +
                "\t\t\t\t</id>\n" +
                "\t\t\t\t<code/>\n" +
                "\t\t\t\t<!--申请单描述-->\n" +
                "\t\t\t\t<text value=\"申请单描述\"/>\n" +
                "\t\t\t\t<!--申请单状态-->\n" +
                "\t\t\t\t<statusCode code=\"active\"/>\n" +
                "\t\t\t\t<!--申请单有效日期时间-->\n" +
                "\t\t\t\t<effectiveTime xsi:type=\"IVL_TS\">\n" +
                "\t\t\t\t\t<low value=\"20120506\"/>\n" +
                "\t\t\t\t\t<high value=\"20120508\"/>\n" +
                "\t\t\t\t</effectiveTime>\n" +
                "\t\t\t\t<!--就诊次数,数值@value，单位@unit-->\n" +
                "\t\t\t\t<lengthOfStayQuantity unit=\"次\" value=\"2\"/>\n" +
                "\t\t\t\t<!--优先级别-->\n" +
                "\t\t\t\t<priorityCode code=\"N\">\n" +
                "\t\t\t\t\t<displayName value=\"常规\"/>\n" +
                "\t\t\t\t</priorityCode>\n" +
                "\t\t\t\t<!--标本-->\n" +
                "\t\t\t\t<specimen>\n" +
                "\t\t\t\t\t<specimen classCode=\"SPEC\">\n" +
                "\t\t\t\t\t\t<!--标本ID/或者称条码ID@extension-->\n" +
                "\t\t\t\t\t\t<id root=\"2.16.156.10011.1.14\" extension=\"@8741795311\"/>\n" +
                "\t\t\t\t\t\t<!--标本类别代码@code和描述-->\n" +
                "\t\t\t\t\t\t<code code=\"1\">\n" +
                "\t\t\t\t\t\t\t<displayName value=\"标本类别名称\"/>\n" +
                "\t\t\t\t\t\t</code>\n" +
                "\t\t\t\t\t\t<!--检验类别代码@code1和描述-->\n" +
                "\t\t\t\t\t\t<code1 code=\"1\">\n" +
                "\t\t\t\t\t\t\t<displayName value=\"检验类别名称\"/>\n" +
                "\t\t\t\t\t\t</code1>\n" +
                "\t\t\t\t\t\t<!--采样方法代码@code2和描述-->\n" +
                "\t\t\t\t\t\t<code2 code=\"1\">\n" +
                "\t\t\t\t\t\t\t<displayName value=\"采样方法名称\"/>\n" +
                "\t\t\t\t\t\t</code2>\n" +
                "\t\t\t\t\t\t<!--采样部位代码@code3和描述-->\n" +
                "\t\t\t\t\t\t<code3 code=\"1\">\n" +
                "\t\t\t\t\t\t\t<displayName value=\"采样部位名称\"/>\n" +
                "\t\t\t\t\t\t</code3>\n" +
                "\t\t\t\t\t\t<!--医嘱执行频率代码@code4和描述-->\n" +
                "\t\t\t\t\t\t<code4 code=\"1\">\n" +
                "\t\t\t\t\t\t\t<displayName value=\"医嘱执行频率\"/>\n" +
                "\t\t\t\t\t\t</code4>\n" +
                "\t\t\t\t\t</specimen>\n" +
                "\t\t\t\t</specimen>\n" +
                "\t\t\t\t<!--开单医生/送检医生 -->\n" +
                "\t\t\t\t<author typeCode=\"AUT\">\n" +
                "\t\t\t\t\t<!-- 开单时间 -->\n" +
                "\t\t\t\t\t<time value=\"20200601135112\"/>\n" +
                "\t\t\t\t\t<!--申请单开立者签名-->\n" +
                "\t\t\t\t\t<signatureText value=\"李医生\"/>\n" +
                "\t\t\t\t\t<assignedEntity classCode=\"ASSIGNED\">\n" +
                "\t\t\t\t\t\t<!--开单医生编码@extension -->\n" +
                "\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t<item extension=\"09882374\" root=\"2.16.156.10011.1.4\"/>\n" +
                "\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t<!--开单医生姓名 -->\n" +
                "\t\t\t\t\t\t<assignedPerson determinerCode=\"INSTANCE\" classCode=\"PSN\">\n" +
                "\t\t\t\t\t\t\t<name xsi:type=\"BAG_EN\">\n" +
                "\t\t\t\t\t\t\t\t<item>\n" +
                "\t\t\t\t\t\t\t\t\t<part value=\"李武\"/>\n" +
                "\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t</assignedPerson>\n" +
                "                        <!-- 登记机构信息 -->\n" +
                "\t\t\t\t\t\t<providerOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">\n" +
                "\t\t\t\t\t\t\t<!--组织机构代码@extension，root为OID固定值-->\n" +
                "\t\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t\t<item root=\"2.16.156.10011.1.5\" extension=\"12500112747487830A\"/>\n" +
                "\t\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t\t<!--组织机构名称@value-->\n" +
                "\t\t\t\t\t\t\t<name xsi:type=\"LIST_EN\">\n" +
                "\t\t\t\t\t\t\t\t<item>\n" +
                "\t\t\t\t\t\t\t\t\t<part value=\"重庆市渝北区人民医院\"/>\n" +
                "\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t\t<contactParty classCode=\"CON\"/>\n" +
                "\t\t\t\t\t\t</providerOrganization>\n" +
                "\t\t\t\t\t\t<!-- 申请科室信息 -->\n" +
                "\t\t\t\t\t\t<representedOrganization determinerCode=\"INSTANCE\" classCode=\"ORG\">\n" +
                "\t\t\t\t\t\t\t<!--申请科室编码@extension 必须项已使用 -->\n" +
                "\t\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t\t<item extension=\"023984\" root=\"2.16.156.10011.1.26\"/>\n" +
                "\t\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t\t<!--申请科室名称 -->\n" +
                "\t\t\t\t\t\t\t<name xsi:type=\"BAG_EN\">\n" +
                "\t\t\t\t\t\t\t\t<item>\n" +
                "\t\t\t\t\t\t\t\t\t<part value=\"骨科\"/>\n" +
                "\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t</representedOrganization>\n" +
                "\t\t\t\t\t</assignedEntity>\n" +
                "\t\t\t\t</author>\n" +
                "\t\t\t\t<!--审核者-->\n" +
                "\t\t\t\t<verifier typeCode=\"VRF\">\n" +
                "\t\t\t\t\t<!--审核日期时间 -->\n" +
                "\t\t\t\t\t<time value=\"201205061000\"/>\n" +
                "\t\t\t\t\t<assignedEntity classCode=\"ASSIGNED\">\n" +
                "\t\t\t\t\t\t<!--审核者编码@extension -->\n" +
                "\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t<item extension=\"9023884\" root=\"2.16.156.10011.1.4\"/>\n" +
                "\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t<assignedPerson determinerCode=\"INSTANCE\" classCode=\"PSN\">\n" +
                "\t\t\t\t\t\t\t<!--审核者姓名 -->\n" +
                "\t\t\t\t\t\t\t<name xsi:type=\"BAG_EN\">\n" +
                "\t\t\t\t\t\t\t\t<item>\n" +
                "\t\t\t\t\t\t\t\t\t<part value=\"李二\"/>\n" +
                "\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t</assignedPerson>\n" +
                "\t\t\t\t\t</assignedEntity>\n" +
                "\t\t\t\t</verifier>\n" +
                "\t\t\t\t<!-- 多个检验项目循环component2 -->\n" +
                "\t\t\t\t<component2>\n" +
                "\t\t\t\t\t<observationRequest classCode=\"OBS\" moodCode=\"RQO\">\n" +
                "\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t<!--医嘱ID@extension-->\n" +
                "\t\t\t\t\t\t\t<item root=\"2.16.156.10011.1.28\" extension=\"111\"/>\n" +
                "\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t<!--检验金额-->\n" +
                "\t\t\t\t\t\t<cost>\n" +
                "\t\t\t\t\t        <item  extension=\"127.2\"/>\n" +
                "\t\t\t\t        </cost>\n" +
                "\t\t\t\t\t\t<!--检验项目编码@code -->\n" +
                "\t\t\t\t\t\t<code code=\"92\">\n" +
                "\t\t\t\t\t\t\t<!--检验项目名称 -->\n" +
                "\t\t\t\t\t\t\t<displayName value=\"血常规\"/>\n" +
                "\t\t\t\t\t\t</code>\n" +
                "\t\t\t\t\t\t<!-- 必须项未使用 -->\n" +
                "\t\t\t\t\t\t<statusCode/>\n" +
                "\t\t\t\t\t\t<methodCode>\n" +
                "\t\t\t\t\t\t\t<!--检验方法编码@code -->\n" +
                "\t\t\t\t\t\t\t<item code=\"94 \">\n" +
                "\t\t\t\t\t\t\t\t<!--检验方法名 -->\n" +
                "\t\t\t\t\t\t\t\t<displayName value=\"检验方法描述\"/>\n" +
                "\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t</methodCode>\n" +
                "\t\t\t\t\t\t<!--执行科室 -->\n" +
                "\t\t\t\t\t\t<location typeCode=\"LOC\">\n" +
                "\t\t\t\t\t\t\t<!--执行时间 -->\n" +
                "\t\t\t\t\t\t\t<time>\n" +
                "\t\t\t\t\t\t\t\t<any value=\"201206060900\"/>\n" +
                "\t\t\t\t\t\t\t</time>\n" +
                "\t\t\t\t\t\t\t<serviceDeliveryLocation classCode=\"SDLOC\">\n" +
                "\t\t\t\t\t\t\t\t<serviceProviderOrganization determinerCode=\"INSTANCE\" classCode=\"ORG\">\n" +
                "\t\t\t\t\t\t\t\t\t<!--执行科室编码@extension -->\n" +
                "\t\t\t\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t\t\t\t<item extension=\"0128384\" root=\"2.16.156.10011.1.26\"/>\n" +
                "\t\t\t\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t\t\t\t<!-- 执行科室名称 -->\n" +
                "\t\t\t\t\t\t\t\t\t<name xsi:type=\"BAG_EN\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<item>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<part value=\"检验科 \"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t\t\t</serviceProviderOrganization>\n" +
                "\t\t\t\t\t\t\t</serviceDeliveryLocation>\n" +
                "\t\t\t\t\t\t</location>\n" +
                "\t\t\t\t\t</observationRequest>\n" +
                "\t\t\t\t</component2>\n" +
                "\t\t\t\t<subjectOf6 contextConductionInd=\"false\">\n" +
                "\t\t\t\t\t<!-- 必须项 未使用 default=false -->\n" +
                "\t\t\t\t\t<seperatableInd value=\"false\"/>\n" +
                "\t\t\t\t\t<!--申请注意事项 -->\n" +
                "\t\t\t\t\t<annotation>\n" +
                "\t\t\t\t\t\t<text value=\"注意XXX\"/>\n" +
                "\t\t\t\t\t\t<statusCode code=\"completed\"/>\n" +
                "\t\t\t\t\t\t<author>\n" +
                "\t\t\t\t\t\t\t<assignedEntity classCode=\"ASSIGNED\"/>\n" +
                "\t\t\t\t\t\t</author>\n" +
                "\t\t\t\t\t</annotation>\n" +
                "\t\t\t\t</subjectOf6>\n" +
                "\t\t\t\t<!--就诊 -->\n" +
                "\t\t\t\t<componentOf1 contextConductionInd=\"false\" typeCode=\"COMP\">\n" +
                "\t\t\t\t\t<!--就诊 -->\n" +
                "\t\t\t\t\t<encounter classCode=\"ENC\" moodCode=\"EVN\">\n" +
                "\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t<!--门急诊号@extension-->\n" +
                "\t\t\t\t\t\t\t<item root=\"2.16.156.10011.1.11\" extension=\"11\"/>\n" +
                "\t\t\t\t\t\t\t<!--住院号@extension-->\n" +
                "\t\t\t\t\t\t\t<item root=\"2.16.156.10011.1.12\" extension=\"11\"/>\n" +
                "\t\t\t\t\t\t\t<!--体检号标识 -->\n" +
                "\t\t\t                <id root=\"1.2.156.112636.1.1.2.2.1.4\" extension=\"10000220\"/>\n" +
                "\t\t\t\t\t\t\t<!--院内就诊卡号 -->\n" +
                "\t\t\t\t\t\t\t<item extension=\"5411122255\"/>\n" +
                "\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t<!--就诊类别代码1.门诊 2.急诊 3.住院 4.体检 9.其他-->\n" +
                "\t\t\t\t\t\t<code code=\"1\" codeSystem=\"2.16.156.10011.2.3.1.271\" codeSystemName=\"患者类型代码表\">\n" +
                "\t\t\t\t\t\t\t<displayName value=\"门诊\"/>\n" +
                "\t\t\t\t\t\t</code>\n" +
                "\t\t\t\t\t\t<!--必须项未使用 -->\n" +
                "\t\t\t\t\t\t<statusCode/>\n" +
                "\t\t\t\t\t\t<!--病人信息 -->\n" +
                "\t\t\t\t\t\t<subject typeCode=\"SBJ\">\n" +
                "\t\t\t\t\t\t\t<patient classCode=\"PAT\">\n" +
                "\t\t\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t\t\t<!-- 患者ID@extension -->\n" +
                "\t\t\t\t\t\t\t\t\t<item root=\"2.16.156.10011.0.2.2\" extension=\"09102312\"/>\n" +
                "\t\t\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t\t\t<!--个人信息 必须项已使用 -->\n" +
                "\t\t\t\t\t\t\t\t<patientPerson classCode=\"PSN\">\n" +
                "\t\t\t\t\t\t\t\t\t<!-- 身份证号/医保卡号 -->\n" +
                "\t\t\t\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t\t\t\t<!-- 身份证号@extension -->\n" +
                "\t\t\t\t\t\t\t\t\t\t<item extension=\"110938197803030456\" root=\"2.16.156.10011.1.3\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t<!-- 医保卡号@extension -->\n" +
                "\t\t\t\t\t\t\t\t\t\t<item extension=\"191284777494877\" root=\"2.16.156.10011.1.15\"/>\n" +
                "\t\t\t\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t\t\t\t<!--姓名 -->\n" +
                "\t\t\t\t\t\t\t\t\t<name xsi:type=\"DSET_EN\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<item>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<part value=\"张三\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t\t\t\t<!-- 联系电话 -->\n" +
                "\t\t\t\t\t\t\t\t\t<telecom xsi:type=\"BAG_TEL\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<!-- 联系电话 -->\n" +
                "\t\t\t\t\t\t\t\t\t\t<item value=\"15801020489\"/>\n" +
                "\t\t\t\t\t\t\t\t\t</telecom>\n" +
                "\t\t\t\t\t\t\t\t\t<!--性别,@code编号，@codeSystem性别的OID固定值-->\n" +
                "\t\t\t\t\t\t\t        <administrativeGenderCode code=\"1\" codeSystem=\"2.16.156.10011.2.3.3.4\">\n" +
                "\t\t\t\t\t\t\t\t        <displayName value=\"男性\"/>\n" +
                "\t\t\t\t\t\t\t        </administrativeGenderCode>\n" +
                "\t\t\t\t\t\t\t        <!--出生日期 -->\n" +
                "\t\t\t\t\t\t\t        <birthTime value=\"19870202\">\n" +
                "\t\t\t\t\t\t\t\t        <!--年龄 -->\n" +
                "\t\t\t\t\t\t\t\t        <originalText value=\"25\" unit=\"岁\"/>\n" +
                "\t\t\t\t\t\t\t        </birthTime>\n" +
                "\t\t\t\t\t\t\t\t\t<!--住址 -->\n" +
                "\t\t\t\t\t\t\t\t\t<addr xsi:type=\"BAG_AD\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<item use=\"H\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<part type=\"AL\" value=\"南京市建邺区\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t\t\t</addr>\n" +
                "\t\t\t\t\t\t\t\t\t<!--婚姻状况,@code编号,@codeSystem婚姻状况的OID固定值-->\n" +
                "\t\t\t\t\t\t            <maritalStatusCode code=\"10\" codeSystem=\"2.16.156.10011.2.3.3.5\">\n" +
                "\t\t\t\t\t\t\t            <displayName value=\"未婚\"/>\n" +
                "\t\t\t\t\t\t            </maritalStatusCode>\n" +
                "\t\t\t\t\t\t            <!--民族,@code编号,@codeSystem民族的OID固定值-->\n" +
                "\t\t\t\t\t\t            <ethnicGroupCode>\n" +
                "\t\t\t\t\t\t\t            <item code=\"01\" codeSystem=\"2.16.156.10011.2.3.3.3\">\n" +
                "\t\t\t\t\t\t\t\t            <displayName value=\"汉族\"/>\n" +
                "\t\t\t\t\t\t\t            </item>\n" +
                "\t\t\t\t\t\t            </ethnicGroupCode>\n" +
                "\t\t\t\t\t\t\t\t</patientPerson>\n" +
                "\t\t\t\t\t\t\t</patient>\n" +
                "\t\t\t\t\t\t</subject>\n" +
                "\t\t\t\t\t\t<!--住院位置-->\n" +
                "\t\t\t\t\t\t<location typeCode=\"LOC\">\n" +
                "\t\t\t\t\t\t\t<time/>\n" +
                "\t\t\t\t\t\t\t<serviceDeliveryLocation classCode=\"SDLOC\">\n" +
                "\t\t\t\t\t\t\t\t<location classCode=\"PLC\" determinerCode=\"INSTANCE\">\n" +
                "\t\t\t\t\t\t\t\t\t<!-- 病床编码@extension-->\n" +
                "\t\t\t\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t\t\t\t<item extension=\"001\"/>\n" +
                "\t\t\t\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t\t\t\t<name xsi:type=\"BAG_EN\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<item use=\"IDE\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<!-- 病床号 -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<part value=\"201\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t\t\t\t<asLocatedEntityPartOf classCode=\"LOCE\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<location classCode=\"PLC\" determinerCode=\"INSTANCE\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<!--病房编号-->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<item extension=\"001\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<name xsi:type=\"BAG_EN\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<item use=\"IDE\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<!-- 病房号 -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<part value=\"201\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t\t\t\t\t</location>\n" +
                "\t\t\t\t\t\t\t\t\t</asLocatedEntityPartOf>\n" +
                "\t\t\t\t\t\t\t\t</location>\n" +
                "\t\t\t\t\t\t\t\t<serviceProviderOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">\n" +
                "\t\t\t\t\t\t\t\t\t<!--科室编号-->\n" +
                "\t\t\t\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t\t\t\t<item extension=\"001\"/>\n" +
                "\t\t\t\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t\t\t\t<name xsi:type=\"BAG_EN\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<item use=\"IDE\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<!-- 科室名称 -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<part value=\"呼吸内科\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t\t\t\t<asOrganizationPartOf classCode=\"PART\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<wholeOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<id>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<!-- 病区编码 -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<item extension=\"001\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</id>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<name xsi:type=\"BAG_EN\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<item use=\"IDE\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<!-- 病区名称 -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<part value=\"1病区 \"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</item>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</name>\n" +
                "\t\t\t\t\t\t\t\t\t\t</wholeOrganization>\n" +
                "\t\t\t\t\t\t\t\t\t</asOrganizationPartOf>\n" +
                "\t\t\t\t\t\t\t\t</serviceProviderOrganization>\n" +
                "\t\t\t\t\t\t\t</serviceDeliveryLocation>\n" +
                "\t\t\t\t\t\t</location>\n" +
                "\t\t\t\t\t\t<!--诊断(检验申请原因) -->\n" +
                "\t\t\t\t\t\t<pertinentInformation1 typeCode=\"PERT\">\n" +
                "\t\t\t\t\t\t\t<observationDx classCode=\"OBS\" moodCode=\"EVN\">\n" +
                "\t\t\t\t\t\t\t\t<!--诊断类别编码 必须项已使用 -->\n" +
                "\t\t\t\t\t\t\t\t<code code=\"7\" codeSystem=\"1.2.156.112635.1.1.29\">\n" +
                "\t\t\t\t\t\t\t\t\t<!--诊断类别名称 -->\n" +
                "\t\t\t\t\t\t\t\t\t<displayName value=\"放射诊断\"/>\n" +
                "\t\t\t\t\t\t\t\t</code>\n" +
                "\t\t\t\t\t\t\t\t<!-- 必须项未使用 -->\n" +
                "\t\t\t\t\t\t\t\t<statusCode code=\"active\"/>\n" +
                "\t\t\t\t\t\t\t\t<!--诊断日期 -->\n" +
                "\t\t\t\t\t\t\t\t<effectiveTime>\n" +
                "\t\t\t\t\t\t\t\t\t<any value=\"20120506\"/>\n" +
                "\t\t\t\t\t\t\t\t</effectiveTime>\n" +
                "\t\t\t\t\t\t\t\t<!-- 疾病编码 必须项已使用 -->\n" +
                "\t\t\t\t\t\t\t\t<value code=\"A18.029+M01.1* \" codeSystem=\"2.16.156.10011.2.3.3.11\">\n" +
                "\t\t\t\t\t\t\t\t\t<!-- 疾病名称 -->\n" +
                "\t\t\t\t\t\t\t\t\t<displayName value=\"膝结核性滑膜炎 \"/>\n" +
                "\t\t\t\t\t\t\t\t</value>\n" +
                "\t\t\t\t\t\t\t</observationDx>\n" +
                "\t\t\t\t\t\t</pertinentInformation1>\n" +
                "\t\t\t\t\t</encounter>\n" +
                "\t\t\t\t</componentOf1>\n" +
                "\t\t\t</observationRequest>\n" +
                "\t\t</subject>\n" +
                "\t</controlActProcess>\n" +
                "</POOR_IN200901UV>\n");
//        paramMap.put("action","UpdateApplicationState");
//        paramMap.put("message", "<request>\n" +
//                "<inputDate>2020-03-16</inputDate>\n" +
//                "<devCode>Phoenix_100</devCode>\n" +
//                "<inspecNo>200316001</inspecNo>\n" +
//                "<barCode>200316717682</barCode>\n" +
//                "<hospid>RSS20191106000000001</hospid>\n" +
//                "</request>");
        InvokeWebserviceUtil.invokeByAxis(wsdlUrl,namespaceURI,localPart,paramMap);
//        invokeByCxf(wsdlUrl,namespaceURI,localPart,"sendReport","<request>\n" +
//                "\t\t\t<inputDate>2020-03-16</inputDate>\n" +
//                "\t\t\t<devCode>Phoenix_100</devCode>\n" +
//                "\t\t\t<inspecNo>200316001</inspecNo>\n" +
//                "\t\t\t<barCode>200316717682</barCode>\n" +
//                "\t\t\t<hospid>RSS20191106000000001</hospid>\n" +
//                "\t\t\t</request>");
        //invokeByHttp(wsdlUrl,namespaceURI,localPart,paramMap);
    }

    /**
     * 方式1.代理类工厂的方式,需要拿到对方的接口
     */
    public static void cl1() {
        try {
            // 接口地址
            String address = "http://localhost:8080/ws/labInfo?wsdl";
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(LabInfoService.class);
            // 创建一个代理接口实现
            LabInfoService cs = (LabInfoService) jaxWsProxyFactoryBean.create();
            // 调用代理接口的方法调用并返回结果
            String result = cs.InspectionRequisitionRelated("");
            System.out.println("返回结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态调用方式
     */
    public static void cl2() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/ws/labInfo?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[0];
        try {
            QName opName = new QName("http://webservice.inter.cdxt.com/", "LISLabInfoServer");
            // invoke("方法名",参数1,参数2,参数3....);   这里直接穿方法名，可能调不通，关键看人家接口有没得命名空间
            objects = client.invoke(opName, "");
            System.out.println("返回数据:" + objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return:  void
     * @author: tangxiaohui
     * @description: axis调用方式  需要axis的jar包
     * @date: 2020/5/26 15:47
     */
    public static void cl3() {
        try {
            String endpoint = "http://localhost:8080/ws/labInfo?wsdl";
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            String parametersName = "RequisitionXml"; 		// 参数名//对应的是 public String printWord(@WebParam(name = "Message") String Message);
//	            call.setOperationName("printWord");  		// 调用的方法名//当这种调用不到的时候,可以使用下面的,加入命名空间名
            call.setOperationName(new QName("http://webservice.inter.cdxt.com/", "LISLabInfoServer"));// 调用的方法名
            call.addParameter(parametersName, org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);//参数名//XSD_STRING:String类型//.IN入参
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); 	// 返回值类型：String
            String message = "123456789";
            String result = (String) call.invoke(new Object[] { message });// 远程调用
            System.out.println("result is " + result);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * @return:  void
     * @author: tangxiaohui
     * @description: http方式调用
     * @date: 2020/5/26 15:48
     */
    public static void cl4() {
        try {
            // 1 指定WebService服务的请求地址：
            String wsUrl = "http://localhost:8080/ws/labInfo?wsdl";
            // 2 创建URL：
            URL url = new URL(wsUrl);
            // 3 建立连接，并将连接强转为Http连接
            URLConnection conn = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) conn;

            // 4，设置请求方式和请求头：
            con.setDoInput(true); // 是否有入参
            con.setDoOutput(true); // 是否有出参
            con.setRequestMethod("POST"); // 设置请求方式
            con.setRequestProperty("content-type", "text/xml;charset=UTF-8");

            // 5，手动构造请求体
            String applicationXml = "";
            String requestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"";
            requestBody += " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"";
            requestBody += " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
            requestBody += "<soapenv:Body>";

            requestBody += "<q0:LISLabInfoServer xmlns:q0=\"http://webservice.inter.cdxt.com/\">";//调用方法 和命名空间
            requestBody += "<RequisitionXml>" +applicationXml + "</RequisitionXml>";//参数
            requestBody += "</q0:LISLabInfoServer>";

            requestBody += "</soapenv:Body>";
            requestBody += "</soapenv:Envelope>";

            // 6，通过流的方式将请求体发送出去：
            OutputStream out = con.getOutputStream();
            out.write(requestBody.getBytes());
            out.close();
            // 7，服务端返回正常：
            int code = con.getResponseCode();
            if (code == 200) {// 服务端返回正常
                InputStream is = con.getInputStream();
                byte[] b = new byte[1024];
                StringBuffer sb = new StringBuffer();
                int len = 0;
                while ((len = is.read(b)) != -1) {
                    String str = new String(b, 0, len, "UTF-8");
                    sb.append(str);
                }
                System.out.println(sb.toString());
                is.close();
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return:  void
     * @throws:
     * @author: Administrator
     * @description: 直接SOAP调用远程的webservice  需要jar
     * @date: 2020/5/22 0022 18:28
     */
    public static void cl5(){
//        URL url = null;
//        try {
//            url = new URL(
//                    "http://192.168.0.100:8080/ca3/services/caSynrochnized");
//        } catch (MalformedURLException mue) {
//            mue.printStackTrace();
//        }
//        // This is the main SOAP object
//        Call soapCall = new Call();
//        // Use SOAP encoding
//        soapCall.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
//        // This is the remote object we're asking for the price
//        soapCall.setTargetObjectURI("urn:xmethods-caSynrochnized");
//        // This is the name of the method on the above object
//        soapCall.setMethodName("getUser");
//        // We need to send the ISBN number as an input parameter to the method
//        Vector soapParams = new Vector();
//
//        // name, type, value, encoding style
//        Parameter isbnParam = new Parameter("userName", String.class, user,
//                null);
//        soapParams.addElement(isbnParam);
//        soapCall.setParams(soapParams);
//        try {
//            // Invoke the remote method on the object
//            Response soapResponse = soapCall.invoke(url, "");
//            // Check to see if there is an error, return "N/A"
//            if (soapResponse.generatedFault()) {
//                Fault fault = soapResponse.getFault();
//                String f = fault.getFaultString();
//                return f;
//            } else {
//                // read result
//                Parameter soapResult = soapResponse.getReturnValue();
//                // get a string from the result
//                return soapResult.getValue().toString();
//            }
//        } catch (SOAPException se) {
//            return se.getMessage();
//        }
    }
}
