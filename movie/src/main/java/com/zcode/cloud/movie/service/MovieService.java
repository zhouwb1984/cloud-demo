package com.zcode.cloud.movie.service;

import com.zcode.cloud.movie.model.UserModel;
import com.zcode.cloud.movie.model.UserMovieModel;

import java.util.List;

/**
 * @author zhouwb
 * @since 2019/1/18
 */
public interface MovieService {

    UserMovieModel findMovieOfUser(Long userId);

    List<UserMovieModel> findMovieOfUsers(Integer age, String name);

    boolean createUser(UserModel user);

}
