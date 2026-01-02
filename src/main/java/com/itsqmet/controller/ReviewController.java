package com.itsqmet.controller;

import com.itsqmet.entity.Movie;
import com.itsqmet.entity.Review;
import com.itsqmet.entity.User;
import com.itsqmet.service.MovieService;
import com.itsqmet.service.ReviewService;
import com.itsqmet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @PostMapping("/save")
    public String saveReview(
            @ModelAttribute Review review,
            @RequestParam(name = "movie_id") Long id,
            Authentication authentication
    ) {
        if (review.getTitle().isEmpty() && review.getContent().isEmpty()) {
            return "redirect:/movies/movie/" + id + "?error";
        }
        String username = authentication.getName();
        User user = userService.getUserByUsername(username).orElseThrow(() -> new RuntimeException("username not found"));
        Movie movie = movieService.findMovieById(id).orElseThrow(() -> new RuntimeException("movie not found"));
        review.setUser(user);
        review.setMovie(movie);
        reviewService.save(review);
        return "redirect:/movies/movie/" + review.getMovie().getId();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReview(
            @PathVariable Long id,
            @RequestParam(name = "movie_id") Long movieId
    ) {
        reviewService.deleteReviewById(id);
        return "redirect:/movies/movie/" + movieId;
    }

    @GetMapping("/edit/{id}")
    public String editReview(Model model, @PathVariable Long id) {
        Review review = reviewService.findReviewById(id).orElseThrow(() -> new RuntimeException("review not found"));
        model.addAttribute("review", review);
        return "pages/editReview";
    }
}
