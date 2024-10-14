package com.improvement.movieflix.services;

import com.improvement.movieflix.projections.MovieProjection;
import com.improvement.movieflix.repositories.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;


    @Mock
    private MovieProjection movieProjection;

    @Mock
    private MovieProjection movieProjection2;


    @BeforeEach
    public void setUp() {

        Mockito.when(movieProjection.getTitle()).thenReturn("Transformers");
        Mockito.when(movieProjection.getSubTitle()).thenReturn("A era dos transformers");
        Mockito.when(movieProjection.getDateYear()).thenReturn(2018);
        Mockito.when(movieProjection.getImgUrl()).thenReturn("http://imagem.transformers");
        Mockito.when(movieProjection.getGenreId()).thenReturn(2L);


        Mockito.when(movieProjection2.getTitle()).thenReturn("Transformers");
        Mockito.when(movieProjection2.getSubTitle()).thenReturn("A era dos transformers");
        Mockito.when(movieProjection2.getDateYear()).thenReturn(2018);
        Mockito.when(movieProjection2.getImgUrl()).thenReturn("http://imagem.transformers");
        Mockito.when(movieProjection2.getGenreId()).thenReturn(2L);

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


}
