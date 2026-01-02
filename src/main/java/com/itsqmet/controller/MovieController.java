package com.itsqmet.controller;

import com.itsqmet.entity.Actor;
import com.itsqmet.entity.Genre;
import com.itsqmet.entity.Movie;
import com.itsqmet.entity.Review;
import com.itsqmet.service.ActorService;
import com.itsqmet.service.GenreService;
import com.itsqmet.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private GenreService genreService;

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
        List<Actor> actors = actorService.findAll();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("movie", movie);
        model.addAttribute("actors", actors);
        model.addAttribute("genres", genres);
        return "pages/movieForm";
    }

    @GetMapping("/movie/{id}")
    public String getMovieById(@PathVariable Long id, Model model) {
        Movie movie = movieService.findMovieById(id).orElseThrow(() -> new RuntimeException("Movie with id " + id + " not found"));
        Review review = new Review();
        model.addAttribute("movie", movie);
        model.addAttribute("review", review);
        return "pages/movieSolo";
    }

    @GetMapping("/edit/{id}")
    public String updateMovie(@PathVariable Long id, Model model) {
        Optional<Movie> movie = movieService.findMovieById(id);
        List<Actor> actors = actorService.findAll();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("actors", actors);
        model.addAttribute("movie", movie);
        model.addAttribute("genres", genres);
        return "pages/movieForm";
    }

    @PostMapping("/saveAllMovie")
    public List<Movie> saveAllMovie(@RequestBody List<Movie> movies) {
        return movieService.saveMultipleMovies(movies);
    }

    @PostMapping("/saveMovie")
    public String saveMovie(
            @Valid @ModelAttribute Movie movie,
            BindingResult result,
            @RequestParam(required = false, name = "genres-selected") List<Long> genreIds
    ) {
        if (result.hasErrors()) {
            return "pages/movieForm";
        }
        if (genreIds != null && !genreIds.isEmpty()) {
            List<Genre> genres = genreService.findAllById(genreIds);
            movie.setGenres(genres);
        } else {
            movie.setGenres(null);
        }
        movieService.saveMovie(movie);
        return "redirect:/movies/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies/admin";
    }
}
