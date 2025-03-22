package com.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    private String id;
    private String name;
    private String location;
    private String about;
    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + about + '\'' +
                '}';
    }

}

