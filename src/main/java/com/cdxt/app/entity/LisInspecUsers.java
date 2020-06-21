package com.cdxt.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author tangxiaohui
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("LIS_INSPEC_USERS")
public class LisInspecUsers extends Model<LisInspecUsers> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("USERNAME")
    private String username;

    @TableField("HIS_ID")
    private String hisId;

    @TableField("USERNAME_CN")
    private String usernameCn;

    @TableField("INSPEC_NAME")
    private String inspecName;

    @TableField("PASSWORD")
    private String password;

    @TableField("DEPT_ID")
    private String deptId;

    @TableField("ENABLED")
    private String enabled;

    @TableField("DEV_CODES")
    private String devCodes;

    @TableField("ROLE")
    private String role;

    @TableField("AUTO_GRAPH")
    private String autoGraph;

    /**
     * 用户权限(暂行)：1：审核权限
     */
    @TableField("PERMISSIONS")
    private String permissions;

    /**
     * 医院id
     */
    @TableField("HOSPITAL_ID")
    private String hospitalId;

    /**
     * 密码更新时间
     */
    @TableField("PUPDATE_TIME")
    private LocalDateTime pupdateTime;

    /**
     * 更改审核密码
     */
    @TableField("CHANGE_AUDIT_PASSWORD")
    private String changeAuditPassword;

    /**
     * app客户端ID
     */
    @TableField("CLIENT_ID")
    private String clientId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
