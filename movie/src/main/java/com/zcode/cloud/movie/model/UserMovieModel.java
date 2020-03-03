package com.zcode.cloud.movie.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author zhouwb
 * @since 2019/1/18
 */
@Builder
@Data
@ToString
public class UserMovieModel {
    private UserModel user;
    private List<MovieModel> movieList;
}
