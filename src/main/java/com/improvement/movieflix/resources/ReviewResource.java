package com.improvement.movieflix.resources;

import com.improvement.movieflix.dto.ReviewDTO;
import com.improvement.movieflix.servicies.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/reviews")
public class ReviewResource {

    private final ReviewService reviewService;


    @GetMapping(value = "/{id}")
    public ResponseEntity<ReviewDTO> findById(@PathVariable Long id) {
        ReviewDTO dto = reviewService.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
