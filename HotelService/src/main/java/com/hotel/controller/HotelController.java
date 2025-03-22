package com.hotel.controller;

import com.hotel.entities.Hotel;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.saveHotel(hotel));
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels(){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAllHotels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable("id") String id, @RequestBody Hotel hotel) {
        Hotel updatedHotel = this.hotelService.updateHotelById(id, hotel);
        return ResponseEntity.status(HttpStatus.OK).body(updatedHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") String id) {
        this.hotelService.deleteHotelByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
