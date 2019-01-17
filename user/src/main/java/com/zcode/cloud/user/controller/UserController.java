package com.zcode.cloud.user.controller;

import com.zcode.cloud.user.model.UserModel;
import com.zcode.cloud.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhouwenbo1@vipkid.com.cn
 * @since 2019/1/17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public UserModel findUser(@PathVariable Long id) {
        return userService.findById(id);
    }

}
