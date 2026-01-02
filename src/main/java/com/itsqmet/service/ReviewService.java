package com.itsqmet.service;

import com.itsqmet.entity.Review;
import com.itsqmet.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review updateReview(Long id, Review review) {
        Review oldReview = findReviewById(id).orElseThrow(() -> new RuntimeException("Review Not Found"));
        oldReview.setContent(review.getContent());
        oldReview.setTitle(review.getTitle());
        oldReview.setReleaseDate(review.getReleaseDate());

        return reviewRepository.save(oldReview);
    }
}
