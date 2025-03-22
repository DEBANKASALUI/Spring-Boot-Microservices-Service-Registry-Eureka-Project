package com.rating.service;

import com.rating.entity.Rating;

import java.util.List;

public interface RatingService {

    //    create
    Rating create(Rating rating);

    //    get all ratings
    List<Rating> getAllRatings();

    //    get all ratings by userId
    List<Rating> getAllRatingByUserId(String userId);

    //    get all ratings by hotelId
    List<Rating> getAllRatingByHotelId(String hotelId);

}


