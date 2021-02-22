package com.damoim.restapi.seminar.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@Embeddable
@Getter @Setter
public class Address {

    private String country;
    private String city;
    private String street;

    protected Address() {}

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }
}
