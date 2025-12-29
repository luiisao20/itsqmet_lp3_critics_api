package com.itsqmet.controller;

import com.itsqmet.entity.Movie;
import com.itsqmet.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    private List<Movie> movies;

    @GetMapping
    public String getMovies(Model model) {
        List<Movie> movies = movieService.showMovies();
        model.addAttribute("movies", movies);
        return "pages/movies";
    }

    @GetMapping("/admin")
    public String getMoviesAdmin(Model model) {
        List<Movie> movies = movieService.showMovies();
        model.addAttribute("movies", movies);
        return "pages/moviesAdmin";
    }

    @GetMapping("/create")
    public String createMovie(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        return "pages/movieForm";
    }

    @GetMapping("/{id}")
    public Optional<Movie> getMovieById(@PathVariable Long id) {
        return movieService.findMovieById(id);
    }

    @GetMapping("/edit/{id}")
    public String updateMovie(@PathVariable Long id, Model model) {
        Optional<Movie> movie = movieService.findMovieById(id);
        model.addAttribute("movie", movie);
        return "pages/movieForm";
    }

    @PostMapping("/saveAllMovie")
    public List<Movie> saveAllMovie(@RequestBody List<Movie> movies) {
        return movieService.saveMultipleMovies(movies);
    }

    @PostMapping("/saveMovie")
    public String saveMovie(@ModelAttribute Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/movies/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies/admin";
    }
}
