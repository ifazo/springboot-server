package api.spring.server.review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")

public class ReviewController {
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/reviews")
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(savedReview);
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        review.setId(id);
        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(savedReview);
    }

    @PatchMapping("/reviews/{id}")
    public ResponseEntity<Review> patchReview(@PathVariable Long id, @RequestBody Review review) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Review updatedReview = existingReview.get();
        if (review.getTitle() != null) {
            updatedReview.setTitle(review.getTitle());
        }
        if (review.getContent() != null) {
            updatedReview.setContent(review.getContent());
        }
        if (review.getRating() != 0) {
            updatedReview.setRating(review.getRating());
        }
        Review savedReview = reviewRepository.save(updatedReview);
        return ResponseEntity.ok(savedReview);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reviewRepository.delete(review.get());
        return ResponseEntity.noContent().build();
    }
}
