package com.cdxt.app.controller;


import com.cdxt.app.entity.LisInspecUsers;
import com.cdxt.app.service.LisInspecUsersService;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/all")
    public List<LisInspecUsers> all(){
        return lisInspecUsersService.list();
    }
}
