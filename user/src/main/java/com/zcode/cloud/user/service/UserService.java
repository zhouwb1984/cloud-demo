package com.zcode.cloud.user.service;

import com.zcode.cloud.user.model.UserModel;

import java.util.List;

/**
 * @since 2019/1/17
 */
public interface UserService {

    UserModel findById(Long id);

    List<UserModel> findByCondition(Integer age, String name);

}
