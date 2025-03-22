package com.hotel.exceptions;

public class HotelNotFoundException extends RuntimeException{
    public HotelNotFoundException() {
        super("Hotel not Found!");
    }

    public HotelNotFoundException(String message) {
        super(message);
    }
}
