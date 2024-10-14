package com.improvement.movieflix.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subTitle;

    private Integer dateYear;

    private String imgUrl;

    @Lob
    private String synopsis;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Review> reviews = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

}
