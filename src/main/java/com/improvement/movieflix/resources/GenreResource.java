package com.improvement.movieflix.resources;

import com.improvement.movieflix.dto.GenreDTO;
import com.improvement.movieflix.servicies.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/genres")
public class GenreResource {

    private final GenreService genreService;


    @GetMapping
    public ResponseEntity<List<GenreDTO>> finAllPaged() {
        List<GenreDTO> list = genreService.findAllPaged();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GenreDTO> findById(@PathVariable Long id) {
        GenreDTO dto = genreService.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
