package com.improvement.movieflix.servicies;

import com.improvement.movieflix.dto.ReviewDTO;
import com.improvement.movieflix.entities.Review;
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

    @Transactional(readOnly = true)
    public ReviewDTO findById(Long id) {
        Review review = getIdIfIdNotNull(id);
        return new ReviewDTO(review);
    }




    public Review getIdIfIdNotNull(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElseThrow(() -> new EntityNotFoundException("Id not Exist"));
    }

}
