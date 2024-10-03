package com.improvement.movieflix.repositories;

import com.improvement.movieflix.entities.Movie;
import com.improvement.movieflix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = """
            select tb_movie.title, 
                   tb_movie.date_year, 
                   tb_movie.sub_title, 
                   tb_movie.img_url
            from tb_movie 
            order by tb_movie.title asc
            """, nativeQuery = true)
    Page<MovieProjection> findMovieOrderByAsc(Pageable pageable);

    Movie findById(long id);

}
