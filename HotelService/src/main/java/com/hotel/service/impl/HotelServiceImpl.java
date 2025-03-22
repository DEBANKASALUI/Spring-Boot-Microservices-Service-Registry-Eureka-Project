package com.hotel.service.impl;

import com.hotel.entities.Hotel;
import com.hotel.exceptions.HotelNotFoundException;
import com.hotel.repository.HotelRepository;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository repository;

    @Override
    public Hotel saveHotel(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return this.repository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return this.repository.findAll();
    }

    @Override
    public Hotel getHotelById(String hotelId) {
        return this.repository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException("Hotel with given id not found in server: " + hotelId));
    }

    @Override
    public Hotel updateHotelById(String id, Hotel updatedHotel) {
        return this.repository.findById(id).map(user -> {
            user.setName(updatedHotel.getName());
            user.setLocation(updatedHotel.getLocation());
            // Update other fields as needed
            return this.repository.save(user);
        }).orElseThrow(() -> new HotelNotFoundException("Hotel with given ID is not found on server: " + id));
    }

    @Override
    public void deleteHotelByID(String id) {
        Hotel hotel = this.repository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("User with given ID is not found on server: " + id));
        this.repository.delete(hotel);
    }

}
