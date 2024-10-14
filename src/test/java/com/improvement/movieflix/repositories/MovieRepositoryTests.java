package com.improvement.movieflix.repositories;

import com.improvement.movieflix.entities.Movie;
import com.improvement.movieflix.projections.MovieProjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestConstructor;

import java.util.Optional;

@DataJpaTest(showSql = false)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class MovieRepositoryTests {

    private final MovieRepository movieRepository;

    public MovieRepositoryTests(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    Long existId;
    Long notExistId;
    Long countTotalMovies;

    Long notRegisteredGenreId;
    Long horrorMovie;
    Long comedyMovie;
    Long dramaMovie;

    @BeforeEach
    public void setUp() {
        existId = 1L;
        notExistId = 100L;
        countTotalMovies = 10L;
        notRegisteredGenreId = 5L;

        comedyMovie = 1L;
        horrorMovie = 2L;
        dramaMovie = 3L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdIsPresent() {
        movieRepository.deleteById(existId);

        Optional<Movie> movie = movieRepository.findById(existId);
        Assertions.assertFalse(movie.isPresent());

    }

    @Test
    public void saveShouldPersistObjectWhenObjectIsNull() {
        Movie movies = new Movie();

        movieRepository.save(movies);

        Movie movie = movieRepository.save(movies);
        Assertions.assertEquals(countTotalMovies + 1, movie.getId());

    }

    @Test
    public void findByIdShouldReturnNullObjectWhenIdNotNull() {
        Optional<Movie> movie = movieRepository.findById(notExistId);
        Assertions.assertTrue(movie.isEmpty());
    }

    @Test
    public void findMovieShouldReturnMoviesByGenre() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MovieProjection> findMovieOrderByAscShould = movieRepository.findMovie(horrorMovie, pageRequest);

        Assertions.assertFalse(findMovieOrderByAscShould.isEmpty());
        Assertions.assertEquals(3, findMovieOrderByAscShould.getTotalElements());

        boolean allMoviesFromRegisteredGenre = findMovieOrderByAscShould.stream().allMatch(x -> x.getGenreId().equals(horrorMovie));
        Assertions.assertTrue(allMoviesFromRegisteredGenre);

    }

    @Test
    public void findMovieShouldReturnEmptyPageWhenGenreDoesNotExists() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MovieProjection> findMovieOrderByAscShould = movieRepository.findMovie(notRegisteredGenreId, pageRequest);

        Assertions.assertTrue(findMovieOrderByAscShould.isEmpty());

    }

    @Test
    public void findMovieShouldReturnAllMoviesWhenGenreIdIsNull() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MovieProjection> findMovieOrderByAscShould = movieRepository.findMovie(null, pageRequest);

        Assertions.assertFalse(findMovieOrderByAscShould.isEmpty());
        Assertions.assertEquals(findMovieOrderByAscShould.getTotalElements(), 10);

        boolean containsHorrorMovie = containsMovieByGenre(findMovieOrderByAscShould, horrorMovie);
        Assertions.assertTrue(containsHorrorMovie);

        boolean containsComedyMovie = containsMovieByGenre(findMovieOrderByAscShould, comedyMovie);
        Assertions.assertTrue(containsComedyMovie);

        boolean containsDramaMovie = containsMovieByGenre(findMovieOrderByAscShould, dramaMovie);
        Assertions.assertTrue(containsDramaMovie);


    }

    private boolean containsMovieByGenre(Page<MovieProjection> findMovieOrderByAscShould, Long genreId) {
        return findMovieOrderByAscShould.stream().anyMatch(x -> x.getGenreId().equals(genreId));
    }

}
