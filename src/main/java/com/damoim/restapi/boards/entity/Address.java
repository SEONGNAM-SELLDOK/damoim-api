package com.damoim.restapi.boards.entity;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gisung go
 * @since 2021-02-22
 */
@Embeddable
@Getter
@Setter
public class Address {

    private String country;
    private String city;
    private String street;

    protected Address() {
    }

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }
}
