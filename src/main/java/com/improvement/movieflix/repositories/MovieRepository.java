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
            tb_movie.date_year as dateYear,
            tb_movie.sub_title as subTitle,
            tb_movie.img_url as imgUrl,
            tb_movie.genre_id as genreId
            from tb_movie
            inner join tb_genre tg on tg.id = tb_movie.genre_id
            where (:genre is null or tg.id = :genre)
            --where genre_id = coalesce(:genre, genre_id)--
            order by tb_movie.title
            """, nativeQuery = true)
    Page<MovieProjection> findMovie(Long genre, Pageable pageable);

}
