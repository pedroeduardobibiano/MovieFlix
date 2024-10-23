package com.improvement.movieflix.services;

import com.improvement.movieflix.dto.GenreDTO;
import com.improvement.movieflix.dto.MovieDTO;
import com.improvement.movieflix.dto.ReviewDTO;
import com.improvement.movieflix.entities.Genre;
import com.improvement.movieflix.entities.Movie;
import com.improvement.movieflix.entities.Review;
import com.improvement.movieflix.entities.User;
import com.improvement.movieflix.projections.MovieProjection;
import com.improvement.movieflix.repositories.GenreRepository;
import com.improvement.movieflix.repositories.MovieRepository;
import com.improvement.movieflix.repositories.ReviewRepository;
import com.improvement.movieflix.services.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieProjection movieProjection;

    @Mock
    private MovieProjection movieProjection2;


    private Movie movie;
    private Movie existingMovie;
    private MovieDTO dto;
    private MovieDTO newDTO;
    private ReviewDTO reviewDTO;


    @BeforeEach
    public void init() {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(2L);
        genreDTO.setName("Action");


        User user = new User();
        user.setId(1L);
        user.setName("User Name");

        reviewDTO = new ReviewDTO();
        reviewDTO.setId(5L);
        reviewDTO.setText("Achei o filme espetacular");
        reviewDTO.setMovie(1L);

        dto = new MovieDTO();
        dto.setId(1L);
        dto.setTitle("Transformers");
        dto.setSubTitle("A era dos transformers");
        dto.setDateYear(2018);
        dto.setImgUrl("http://imagem.transformers");
        dto.setGenre(genreDTO);

        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setName(genreDTO.getName());

        user = new User();
        user.setId(1L);
        user.setName("User Name");

        Review review = new Review();
        review.setId(5L);
        review.setText("Achei o filme espetacular");
        review.setUser(user);
        review.setMovie(movie);


        movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setSubTitle(dto.getSubTitle());
        movie.setDateYear(dto.getDateYear());
        movie.setImgUrl(dto.getImgUrl());
        movie.setGenre(genre);


        newDTO = new MovieDTO();
        newDTO.setId(15L);
        newDTO.setTitle("Old Title");
        newDTO.setSubTitle("Old Subtitle");
        newDTO.setDateYear(2020);
        newDTO.setImgUrl("old_image_url");
        newDTO.setSynopsis("Old Synopsis");
        newDTO.setGenre(genreDTO);

        existingMovie = new Movie();
        existingMovie.setId(15L);
        existingMovie.setTitle("Old Title");
        existingMovie.setSubTitle("Old Subtitle");
        existingMovie.setDateYear(2020);
        existingMovie.setImgUrl("old_image_url");
        existingMovie.setSynopsis("Old Synopsis");
        existingMovie.setGenre(genre);
    }


    @Test
    public void findMoviePagedWithSomeGenreFilterShouldReturnPage() {
        PageImpl<MovieProjection> page = new PageImpl<>(List.of(movieProjection));
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(movieRepository.findMovie(2L, pageable)).thenReturn(page);


        Page<MovieProjection> result = movieService.findMoviesPaged(2L, pageable);
        Assertions.assertNotNull(result);

        Mockito.verify(movieRepository, Mockito.times(1)).findMovie(2L, pageable);

    }

    @Test
    public void findMoviePagedWithoutGenreFilterShouldReturnPage() {
        PageImpl<MovieProjection> page = new PageImpl<>(List.of(movieProjection, movieProjection2));
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(movieRepository.findMovie(null, pageable)).thenReturn(page);

        Page<MovieProjection> result = movieService.findMoviesPaged(null, pageable);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getContent().size());

        Mockito.verify(movieRepository, Mockito.times(1)).findMovie(null, pageable);
    }

    @Test
    public void findMovieByIdShouldReturnMovie() {
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Assertions.assertDoesNotThrow(() -> movieService.findMovieById(1L));

        Mockito.verify(movieRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    public void findMovieByIdShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        Mockito.when(movieRepository.findById(15L)).thenReturn(Optional.of(movie));

        Assertions.assertThrows(EntityNotFoundException.class, () -> movieService.findMovieById(10L));

        Mockito.verify(movieRepository, Mockito.times(1)).findById(10L);

    }

    @Test
    public void createMovieObjectShouldReturnMovieObject() {
        Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);

        Assertions.assertDoesNotThrow(() -> {
            movieService.insert(dto);
        });

        Assertions.assertEquals(dto.getId(), 1L);
        Assertions.assertEquals(dto.getTitle(), "Transformers");
        Assertions.assertEquals(dto.getSubTitle(), "A era dos transformers");

        Assertions.assertEquals(dto.getDateYear(), movie.getDateYear());

        Mockito.verify(movieRepository, Mockito.times(1)).save(Mockito.any(Movie.class));


    }

    @Test
    public void updateMovieObjectShouldReturnMovieObject() {
        Mockito.when(movieRepository.findById(15L)).thenReturn(Optional.of(existingMovie));

        Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);

        Assertions.assertDoesNotThrow(() -> {
            movieService.update(15L, newDTO);
        });

        Assertions.assertEquals(newDTO.getId(), 15L);
        Assertions.assertEquals(newDTO.getTitle(), "Old Title");

        Mockito.verify(movieRepository, Mockito.times(1)).findById(15L);

    }

    @Test
    public void updateMovieObjectShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        Mockito.when(movieRepository.findById(15L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> movieService.update(15L, newDTO));

        Mockito.verify(movieRepository, Mockito.times(1)).findById(15L);
    }


    @Test
    public void deleteMovieByIdShouldDeleteMovieWhenIdExist() {
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Assertions.assertDoesNotThrow(() -> {
            movieService.delete(1L);
        });

        Mockito.verify(movieRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    public void deleteShouldThrowNewExceptionWhenIdDoesNo() {
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Assertions.assertDoesNotThrow(() -> {
            movieService.delete(1L);
        });

        Mockito.verify(movieRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    public void getGenreShouldGetGenreOfMovieSelectById() {
        Mockito.when(movieRepository.findById(2L)).thenReturn(Optional.of(movie));

        Assertions.assertDoesNotThrow(() -> {
            movieService.getMovieGenre(2L);
        });

        Assertions.assertEquals(movie.getGenre().getId(), 2L);

    }

    @Test
    public void getGenreShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        Mockito.when(movieRepository.findById(2L)).thenReturn(Optional.of(movie));

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            movieService.getMovieGenre(10L);
        });

        Assertions.assertEquals(movie.getGenre().getId(), 2L);
    }

    @Test
    public void getReviewShouldGetReviewOfMovieSelectById() {
        Mockito.when(movieRepository.findById(5L)).thenReturn(Optional.of(movie));

        Assertions.assertDoesNotThrow(() -> {
            movieService.getMovieReview(5L);
        });


    }

    @Test
    public void getReviewMovieShouldReturnMovieWhenIdExists() {

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Assertions.assertDoesNotThrow(() -> {
            movieService.getReviewMovie(reviewDTO);
        });

        Assertions.assertEquals(reviewDTO.getId(), 5L);
        Assertions.assertEquals(reviewDTO.getText(), "Achei o filme espetacular");

        Mockito.verify(movieRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void getReviewMovieShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {

        Mockito.when(movieRepository.findById(10L)).thenReturn(Optional.of(movie));

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            movieService.getReviewMovie(reviewDTO);
        });

        Mockito.verify(movieRepository, Mockito.times(1)).findById(1L);
    }


}
