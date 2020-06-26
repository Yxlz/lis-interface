package com.cdxt.app.controller;


import com.cdxt.app.entity.LisInspecUsers;
import com.cdxt.app.enums.IFStateEnum;
import com.cdxt.app.enums.ReturnCodeEnum;
import com.cdxt.app.model.response.BaseResponse;
import com.cdxt.app.service.LisInspecUsersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author tangxiaohui
 * @since 2020-06-21
 */
@RestController
@RequestMapping("/users")
public class LisInspecUsersController {

    @Resource
    LisInspecUsersService lisInspecUsersService;

    @RequestMapping(value = "/all")
    public List<LisInspecUsers> all(){
        return lisInspecUsersService.list();
    }

    @RequestMapping(value = "/all1")
    public BaseResponse all1(){
        return new BaseResponse(IFStateEnum.SUCCESS, ReturnCodeEnum.SUCCESS, "成功", lisInspecUsersService.list());
    }

    @RequestMapping(value = "/insertUser")
    public BaseResponse insert(){
        LisInspecUsers user = new LisInspecUsers();
        user.setUsername("test");
        user.setUsernameCn("测试");
        user.setDeptId("LIS20170214006560861");
        user.setRole("2");
        lisInspecUsersService.save(user);
        return new BaseResponse(IFStateEnum.SUCCESS, ReturnCodeEnum.SUCCESS, "成功", lisInspecUsersService.list());
    }
}
