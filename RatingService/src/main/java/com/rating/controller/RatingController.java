package com.rating.controller;

import com.rating.entity.Rating;
import com.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getAllRatingbyUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRatingByUserId(userId));
    }

    @GetMapping(("/hotels/{hotelId}"))
    public ResponseEntity<List<Rating>> getAllRatingsbyHotelId(@PathVariable("hotelId") String hotelId) {
        return ResponseEntity.ok(ratingService.getAllRatingByHotelId(hotelId));
    }

}
