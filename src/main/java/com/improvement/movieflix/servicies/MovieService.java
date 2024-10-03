package com.improvement.movieflix.servicies;

import com.improvement.movieflix.dto.GenreDTO;
import com.improvement.movieflix.dto.MovieDTO;
import com.improvement.movieflix.entities.Genre;
import com.improvement.movieflix.entities.Movie;
import com.improvement.movieflix.projections.MovieProjection;
import com.improvement.movieflix.repositories.GenreRepository;
import com.improvement.movieflix.repositories.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;


    @Transactional(readOnly = true)
    public Page<MovieProjection> findMoviesPaged(Pageable pageable) {
        return movieRepository.findMovieOrderByAsc(pageable);
    }


    @Transactional(readOnly = true)
    public MovieDTO findMovieById(Long id) {
        Movie movie = getIdIfIdNotNull(id);
        return new MovieDTO(movie);
    }

    @Transactional
    public MovieDTO insert(MovieDTO movieDTO) {
        Movie movie = new Movie();
        setMovieData(movie, movieDTO);
        movie = movieRepository.save(movie);
        return new MovieDTO(movie);
    }

    @Transactional
    public MovieDTO update(Long id, MovieDTO movieDTO) {
        Movie movie = getIdIfIdNotNull(id);
        setMovieData(movie, movieDTO);
        movie = movieRepository.save(movie);
        return new MovieDTO(movie);
    }

    @Transactional
    public void delete(Long id) {
        Movie movie = getIdIfIdNotNull(id);
        movieRepository.delete(movie);
    }

    @Transactional(readOnly = true)
    public GenreDTO getMovieGenre(Long id) {
        Movie movie = getIdIfIdNotNull(id);
        Genre genre = movie.getGenre();
        return new GenreDTO(genre);
    }


    @Transactional(readOnly = true)
    public Movie getIdIfIdNotNull(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElseThrow(() -> new EntityNotFoundException("Movie not found"));
    }


    public void setMovieData(Movie movie, MovieDTO dto) {
        movie.setTitle(dto.getTitle());
        movie.setSubTitle(dto.getSubTitle());
        movie.setDateYear(dto.getDateYear());
        movie.setImgUrl(dto.getImgUrl());
        movie.setSynopsis(dto.getSynopsis());

        Genre genre = genreRepository.getReferenceById(dto.getGenre().getId());
        movie.setGenre(genre);
    }

}
