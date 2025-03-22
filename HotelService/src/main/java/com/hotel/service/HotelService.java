package com.hotel.service;

import com.hotel.entities.Hotel;
import java.util.List;

public interface HotelService {

    Hotel saveHotel(Hotel hotel);

    List<Hotel> getAllHotels();

    Hotel getHotelById(String hotelId);

    Hotel updateHotelById(String id, Hotel hotel);

    void deleteHotelByID(String id);

}
