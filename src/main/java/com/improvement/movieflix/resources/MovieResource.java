package com.improvement.movieflix.resources;

import com.improvement.movieflix.dto.GenreDTO;
import com.improvement.movieflix.dto.MovieDTO;
import com.improvement.movieflix.projections.MovieProjection;
import com.improvement.movieflix.servicies.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/movies")
public class MovieResource {

    private final MovieService movieService;


    @GetMapping
    public ResponseEntity<Page<MovieProjection>> findAllPagedAsc(Pageable pageable) {
        Page<MovieProjection> list = movieService.findMoviesPaged(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
        MovieDTO dto = movieService.findMovieById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> save(@RequestBody MovieDTO dto) {
        MovieDTO movieDto = movieService.insert(dto);
        return new ResponseEntity<>(movieDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody MovieDTO dto) {
        MovieDTO movieDto = movieService.update(id, dto);
        return new ResponseEntity<>(movieDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> deleteById(@PathVariable Long id) {
        movieService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/genres")
    public ResponseEntity<GenreDTO> findGenres(@PathVariable Long id) {
        GenreDTO dto = movieService.getMovieGenre(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
