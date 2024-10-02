package com.improvement.movieflix.entities;

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

    @Column(name="´year´")
    private Integer year;

    private String imgUrl;

    @Lob
    private String synopsis;

    @OneToMany
    private Set<Review> reviews = new HashSet<Review>();

    @ManyToOne
    private Genre genre;

}
