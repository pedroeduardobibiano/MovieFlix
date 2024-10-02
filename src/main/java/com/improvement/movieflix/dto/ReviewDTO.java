package com.improvement.movieflix.dto;


import com.improvement.movieflix.entities.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {

    private Long id;
    private String text;

    public ReviewDTO(Review review) {
        id = review.getId();
        text = review.getText();
    }

}
