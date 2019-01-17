package com.zcode.cloud.user.service;

import com.zcode.cloud.user.dao.UserDao;
import com.zcode.cloud.user.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhouwenbo1@vipkid.com.cn
 * @since 2019/1/17
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public UserModel findById(Long id) {
        UserModel user = userDao.getOne(id);
        log.info(user.toString());
        return user;
    }
}
