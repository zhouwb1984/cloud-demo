package com.zcode.cloud.movie.service;

import com.zcode.cloud.movie.constant.PostMode;
import com.zcode.cloud.movie.handler.UserHandler;
import com.zcode.cloud.movie.model.MovieModel;
import com.zcode.cloud.movie.model.UserModel;
import com.zcode.cloud.movie.model.UserMovieModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouwb
 * @since 2019/1/18
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Resource
    private UserHandler userHandler;

    @Override
    public UserMovieModel findMovieOfUser(Long userId) {
        UserModel user = userHandler.getUser(userId);
        List<MovieModel> movieList = Arrays.asList(MovieModel.builder()
                .id(1L)
                .name("明天的未来")
                .build());
        return UserMovieModel.builder()
                .user(user)
                .movieList(movieList)
                .build();
    }

    @Override
    public List<UserMovieModel> findMovieOfUsers(Integer age, String name) {

        List<UserModel> userList = userHandler.getUsers(age, name);
        List<UserMovieModel> umList = new ArrayList<>();

        for (UserModel user : userList) {
            List<MovieModel> movieList = Arrays.asList(MovieModel.builder()
                    .id(1L)
                    .name("明天的未来")
                    .build());
            umList.add(UserMovieModel.builder()
                    .user(user)
                    .movieList(movieList)
                    .build());
        }
        return umList;
    }

    @Override
    public boolean createUser(UserModel user) {
        String flag = "webClient";
        if (flag.equals("rest")) {
            return userHandler.createUserAsync(user);
        }
        else if (flag.equals("webClient")) {
            return userHandler.createUserWebClient(user);
        }
        return userHandler.createUser(user, PostMode.EXCHANGE_JSON);
    }
}
