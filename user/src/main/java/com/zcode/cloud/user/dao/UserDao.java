package com.zcode.cloud.user.dao;

import com.zcode.cloud.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @since 2019/1/17
 */
@Repository
public interface UserDao extends JpaRepository<UserModel, Long> {
}
