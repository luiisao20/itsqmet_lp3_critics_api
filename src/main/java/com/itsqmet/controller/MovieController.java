package com.itsqmet.controller;

import com.itsqmet.entity.Movie;
import com.itsqmet.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:5173", methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
})
public class MovieController {
    @Autowired
    private MovieService movieService;

    private List<Movie> movies;

    @GetMapping
    public List<Movie> getMovies() {
        return movieService.showMovies();
    }

    @GetMapping("/{id}")
    public Optional<Movie> getMovieById(@PathVariable Long id) {
        return movieService.findMovieById(id);
    }

    @PostMapping("/saveAllMovie")
    public List<Movie> saveAllMovie(@RequestBody List<Movie> movies) {
        return movieService.saveMultipleMovies(movies);
    }

    @PostMapping("/saveMovie")
    public Movie saveMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @PutMapping("/updateMovie/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return movieService.updateMovie(id, movie);
    }

    @DeleteMapping("/deleteMovie/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
