package com.zcode.cloud.user.controller;

import com.zcode.cloud.user.model.UserModel;
import com.zcode.cloud.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @since 2019/1/17
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public UserModel findUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/get-user-by-condition")
    public List<UserModel> findUsers(@RequestParam("age") Integer age,
                         @RequestParam("name") String name) {
        log.info("{},{}", age, name);
        return userService.findByCondition(age,name);
    }

    @PostMapping("/create-user-map")
    public Boolean createUser(@RequestBody Map<String, Object> user) {
        log.info("create user parameters:{}", user);
        return true;
    }

    @PostMapping("/create-user-model")
    public Boolean createUser(@RequestBody UserModel user) {
        log.info("create user parameters:{}", user);
        return true;
    }

    @PostMapping("/create-user-param")
    public Boolean createUserWithParam(UserModel user) {
        log.info("create user parameters:{}", user);
        return true;
    }

}
