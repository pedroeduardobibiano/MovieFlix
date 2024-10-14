package com.improvement.movieflix.services;

import com.improvement.movieflix.dto.ReviewDTO;
import com.improvement.movieflix.entities.Movie;
import com.improvement.movieflix.entities.Review;
import com.improvement.movieflix.entities.User;
import com.improvement.movieflix.repositories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final MovieService movieService;


    @Transactional(readOnly = true)
    public ReviewDTO findById(Long id) {
        Review review = getIdIfIdNotNull(id);
        return new ReviewDTO(review);
    }

    @Transactional(readOnly = true)
    public Review getIdIfIdNotNull(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElseThrow(() -> new EntityNotFoundException("Id not Exist"));
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO reviewDTO) {
        Review review = new Review();
        setReviewToGetReviewDTO(review, reviewDTO);
        Review savedReview = reviewRepository.save(review);
        return new ReviewDTO(savedReview);
    }

    @Transactional
    public void setReviewToGetReviewDTO(Review review, ReviewDTO reviewDTO) {
        User user = userService.getReviewAuthor(reviewDTO);
        Movie movie = movieService.getReviewMovie(reviewDTO);

        review.setText(reviewDTO.getText());
        review.setUser(user);
        review.setMovie(movie);
    }


}
