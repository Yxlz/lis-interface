package com.cdxt.inter;

import com.cdxt.inter.model.InspecRequisitionInfo;
import com.cdxt.inter.model.XmlMessage;
import com.cdxt.inter.util.dom4j.Hl7xml2Bean;
import org.dom4j.Document;

import java.util.List;

/**
 * @Description: TODO
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 13:05
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class XmlParseTest {
    public static void main(String[] args) {
        String xml = "<POOR_IN200901UV ITSVersion=\"XML_1.0\" xmlns=\"urn:hl7-org:v3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:hl7-org:v3 ../../Schemas/POOR_IN200901UV.xsd\">\n" +
                "\t<!-- 消息ID-->\n" +
                "\t<id extension=\"@GUID \"/>\n" +
                "\t<!-- 消息创建时间 -->\n" +
                "\t<creationTime value=\"20150701000000\"/>\n" +
                "\t<!-- 服务标识,extension为唯一事件编码，区分服务事件 -->\n" +
                "\t<interactionId root=\"\" extension=\" JH0206\"/>\n" +
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
                "\t\t\t\t<item root=\"2.16.156.10011.0.1.1\" extension=\" \"/>\n" +
                "\t\t\t</id>\n" +
                "\t\t</device>\n" +
                "\t</receiver>\n" +
                "\t<!-- 发送者 -->\n" +
                "\t<sender typeCode=\"SND\">\n" +
                "\t\t<device classCode=\"DEV\" determinerCode=\"INSTANCE\">\n" +
                "\t\t\t<!-- 发送方ID-->\n" +
                "\t\t\t<id>\n" +
                "\t\t\t\t<item root=\"2.16.156.10011.0.1.2\" extension=\" \"/>\n" +
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
                "\t\t\t\t\t<time value=\"201205061000\"/>\n" +
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
                "</POOR_IN200901UV>\n";

        try {
            Document doc = Hl7xml2Bean.parseXml2Document(xml);
            XmlMessage mainInfo = (XmlMessage) Hl7xml2Bean.parseXml(doc.getRootElement(), XmlMessage.class, false);
            InspecRequisitionInfo reqInfo = (InspecRequisitionInfo) Hl7xml2Bean.parseXml_controlActProcess(doc.getRootElement(), InspecRequisitionInfo.class);
            mainInfo.setSubject(reqInfo);
            System.out.println(reqInfo.getBarCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
