package com.zcode.cloud.movie.controller;

import com.zcode.cloud.movie.dto.ResponseResult;
import com.zcode.cloud.movie.model.UserModel;
import com.zcode.cloud.movie.model.UserMovieModel;
import com.zcode.cloud.movie.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouwb
 * @since 2019/1/18
 */
@RestController
@RequestMapping("/movie")
@Slf4j
public class MovieController {

    @Resource
    private MovieService movieService;

    @GetMapping("/user-movies")
    public ResponseResult getMoviesOfUser() {
        log.info("user-movies");
        Long userId = 1L;
        try {
            UserMovieModel movies = movieService.findMovieOfUser(userId);
            return ResponseResult.success(movies);
        } catch (Exception e) {
            log.error("failed.", e);
            return ResponseResult.failed("Get Movies Failed!");
        }
    }

    @GetMapping("/users-movies")
    public ResponseResult getMoviesOfUsers(Integer age, String nameOfUser) {
        try {
            List<UserMovieModel> umList = movieService.findMovieOfUsers(age, nameOfUser);
            return ResponseResult.success(umList);
        } catch (Exception e) {
            log.error("failed.", e);
            return ResponseResult.failed("Get Movies Failed!");
        }
    }

    @PostMapping("/create-user")
    public ResponseResult createUser(UserModel user) {
        try {
            boolean result = movieService.createUser(user);
            return ResponseResult.success(result);
        } catch (Exception e) {
            log.error("failed.", e);
            return ResponseResult.failed("Create User Failed!");
        }
    }
}
