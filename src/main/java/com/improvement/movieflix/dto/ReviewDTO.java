package com.improvement.movieflix.dto;


import com.improvement.movieflix.entities.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    @NotBlank
    @Size(min = 10, max = 150, message = "this field can't be null")
    private String text;

    private Long user;

    private Long movie;

    public ReviewDTO(Review review) {
        id = review.getId();
        text = review.getText();
        user = review.getUser().getId();
        movie = review.getMovie().getId();

    }

}
