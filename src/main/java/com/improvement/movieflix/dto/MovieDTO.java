package com.improvement.movieflix.dto;

import com.improvement.movieflix.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDTO {

    private Long id;
    private String title;
    private String subTitle;
    private Integer year;
    private String imgUrl;
    private String synopsis;

    public MovieDTO(Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        subTitle = movie.getSubTitle();
        year = movie.getYear();
        imgUrl = movie.getImgUrl();
        synopsis = movie.getSynopsis();
    }


}
