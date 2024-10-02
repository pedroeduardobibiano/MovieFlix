package com.improvement.movieflix.dto;


import com.improvement.movieflix.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreDTO {

    private Long id;
    private String name;


    public GenreDTO(Genre genre) {
        id = genre.getId();
        name = genre.getName();
    }

}
