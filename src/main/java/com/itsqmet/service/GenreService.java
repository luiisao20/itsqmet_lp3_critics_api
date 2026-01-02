package com.itsqmet.service;

import com.itsqmet.entity.Genre;
import com.itsqmet.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public List<Genre> findAllById(List<Long> genreIds) {
        return genreRepository.findAllById(genreIds);
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre updateGenre(Long id, Genre genre) {
        Genre oldGenre = findById(id).orElseThrow(() -> new RuntimeException("Genre not found"));
        oldGenre.setName(genre.getName());
        return genreRepository.save(oldGenre);
    }

    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }

    @Transactional
    public Genre getGenreWithMovie(Long id){
        return findById(id).orElseThrow(() ->  new RuntimeException("Genre not found"));
    }
}
