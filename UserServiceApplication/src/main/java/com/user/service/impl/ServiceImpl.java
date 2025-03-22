package com.user.service.impl;

import com.user.entity.Hotel;
import com.user.entity.Rating;
import com.user.entity.User;
import com.user.exceptions.ResourceNotFoundException;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    @Override
    public User saveUser(User user) {
//        generate Unique User ID
        String randomUserID = UUID.randomUUID().toString();
        user.setUserId(randomUserID);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        // Fetch all users from the repository
        List<User> users = this.userRepository.findAll();
        // For each user, fetch their ratings and associated hotels
        users.forEach(user -> {
            // Fetch ratings for the user as an array of Rating
            Rating[] ratingsArray = restTemplate.getForObject(
                    "http://RatingService/ratings/users/" + user.getUserId(),
                    Rating[].class
            );
            logger.info("{}", ratingsArray);
            // Convert the array to a list and process each rating
            List<Rating> ratingsWithHotels = Arrays.stream(ratingsArray).map(rating -> {
                // API call to Hotel Service to get Hotel details
                ResponseEntity<Hotel> responseEntity = restTemplate.getForEntity(
                        "http://HotelService/hotels/" + rating.getHotelId(),
                        Hotel.class
                );
                Hotel hotel = responseEntity.getBody();

                // Log and set the hotel details to the rating
                logger.info("Hotel Details for User {}: {}", rating.getUserId(), hotel);
                rating.setHotel(hotel);

                return rating; // Return the updated rating
            }).collect(Collectors.toList());

            // Set the updated ratings (with hotels) to the user
            user.setRatings(ratingsWithHotels);
        });
        return users;
    }

    @Override
    public User getUserById(String userId) {
//       get User from Database with the help of User Repository
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID is not found on server: " + userId));
//        fetch rating of above user from Rating Service
//         http://localhost:8083/ratings/users/010d6ae2-8f7f-4d72-b75f-0fe091ef00ae
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RatingService/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("{}", ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        logger.info("{}", ratings);

        List<Rating> ratingList = ratings.stream().map(rating -> {
//      API Call to Hotel Service to get Hotel
//      http://localhost:8082/hotels/07c6f9c3-2921-4847-98a1-9c5e959f1f4c
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HotelService/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("Response Status Code: {} ", forEntity.getStatusCode());
//      Set the Hotel to Rating
            rating.setHotel(hotel);
//      Return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }

    @Override
    public User updateUserById(String id, User updatedUser) {
        return this.userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            // Update other fields as needed
            return this.userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User with given ID is not found on server: " + id));
    }

    @Override
    public void deleteUserByID(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with given ID is not found on server: " + id));
        this.userRepository.delete(user);
    }

}
