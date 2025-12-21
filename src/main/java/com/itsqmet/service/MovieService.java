package com.itsqmet.service;

import com.itsqmet.entity.Movie;
import com.itsqmet.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    // Get all movies
    public List<Movie> showMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> saveMultipleMovies(List<Movie> movies) {
        return movieRepository.saveAll(movies);
    }

    // Find movie by title
    public List<Movie> findMovieByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return movieRepository.findAll();
        } else {
            return movieRepository.findByTitleContainingIgnoreCase(title);
        }
    }

    // Get movie by ID
    public Optional<Movie> findMovieById(Long id) {
        return movieRepository.findById(id);
    }

    // Save new movie
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Delete movie
    public void deleteMovie(Long id) {
        Movie movie = findMovieById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        movieRepository.deleteById(id);
    }

    // Update movie
    public Movie updateMovie(Long id, Movie movie) {
        Movie oldMovie = findMovieById(id).orElseThrow(() -> new RuntimeException("Movie does not exist"));

        oldMovie.setTitle(movie.getTitle());
        oldMovie.setOverview(movie.getOverview());
        oldMovie.setReleaseDate(movie.getReleaseDate());
        oldMovie.setStatus(movie.getStatus());
        oldMovie.setRunTime(movie.getRunTime());
        oldMovie.setImageLink(movie.getImageLink());

        return movieRepository.save(oldMovie);
    }
}
