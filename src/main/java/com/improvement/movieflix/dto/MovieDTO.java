package com.improvement.movieflix.dto;

import com.improvement.movieflix.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class MovieDTO {

    private Long id;
    private String title;
    private String subTitle;
    private Integer dateYear;
    private String imgUrl;
    private String synopsis;

    private GenreDTO genre;

    private Set<ReviewDTO> reviews = new HashSet<>();

    public MovieDTO(Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        subTitle = movie.getSubTitle();
        dateYear = movie.getDateYear();
        imgUrl = movie.getImgUrl();
        synopsis = movie.getSynopsis();
        genre = new GenreDTO(movie.getGenre());
        movie.getReviews().forEach(review -> this.reviews.add(new ReviewDTO(review)));
    }


}
