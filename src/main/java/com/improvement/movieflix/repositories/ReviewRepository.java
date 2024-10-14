package com.improvement.movieflix.repositories;

import com.improvement.movieflix.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {


}
