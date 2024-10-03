package com.improvement.movieflix.servicies;

import com.improvement.movieflix.dto.GenreDTO;
import com.improvement.movieflix.entities.Genre;
import com.improvement.movieflix.repositories.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;


    @Transactional(readOnly = true)
    public List<GenreDTO> findAllPaged() {
        List<Genre> list = genreRepository.findAll();
        return list.stream().map(GenreDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public GenreDTO findById(Long id) {
        Genre genre = getIdIfIdNotNull(id);
        return new GenreDTO(genre);
    }

    @Transactional(readOnly = true)
    public Genre getIdIfIdNotNull(Long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.orElseThrow(() -> new EntityNotFoundException("Genre not found"));
    }


}
