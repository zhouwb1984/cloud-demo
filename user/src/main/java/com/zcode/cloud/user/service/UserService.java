package com.zcode.cloud.user.service;

import com.zcode.cloud.user.model.UserModel;

/**
 * @author zhouwenbo1@vipkid.com.cn
 * @since 2019/1/17
 */
public interface UserService {

    UserModel findById(Long id);

}
