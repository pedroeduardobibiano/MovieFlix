package com.improvement.movieflix.services;

import com.improvement.movieflix.dto.GenreDTO;
import com.improvement.movieflix.dto.MovieDTO;
import com.improvement.movieflix.dto.ReviewDTO;
import com.improvement.movieflix.entities.Genre;
import com.improvement.movieflix.entities.Movie;
import com.improvement.movieflix.entities.Review;
import com.improvement.movieflix.projections.MovieProjection;
import com.improvement.movieflix.repositories.GenreRepository;
import com.improvement.movieflix.repositories.MovieRepository;
import com.improvement.movieflix.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;


    @Transactional(readOnly = true)
    public Page<MovieProjection> findMoviesPaged(Long genreId, Pageable pageable) {
        return movieRepository.findMovie(genreId, pageable);
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
    public ReviewDTO getMovieReview(Long id) {
        Movie movie = getIdIfIdNotNull(id);
        Set<Review> review = movie.getReviews();
        for (Review reviews : review) {
            return new ReviewDTO(reviews);
        }
        return null;
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

    @Transactional
    public Movie getReviewMovie(ReviewDTO reviewDTO) {
        return movieRepository.findById(reviewDTO.getMovie())
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
    }


}
