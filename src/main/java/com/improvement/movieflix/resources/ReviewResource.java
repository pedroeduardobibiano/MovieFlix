package com.improvement.movieflix.resources;

import com.improvement.movieflix.dto.ReviewDTO;
import com.improvement.movieflix.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ReviewDTO> insert(@Valid @RequestBody ReviewDTO dto) {
        dto = reviewService.insert(dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

}
