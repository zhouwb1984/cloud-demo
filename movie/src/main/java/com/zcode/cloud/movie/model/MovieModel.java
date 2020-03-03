package com.zcode.cloud.movie.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhouwb
 * @since 2019/1/18
 */
@Builder
@Data
public class MovieModel {

    private Long id;

    private String name;

}
