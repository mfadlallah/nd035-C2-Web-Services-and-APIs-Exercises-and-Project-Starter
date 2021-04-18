package com.udacity.boogle.maps;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Declares a class to store an address, city, state and zip code.
 */
@Entity
public class Address {
    @Id
    private Long vehicleid;
    private Double lat;
    private Double lon;
    private String address;
    private String city;
    private String state;
    private String zip;

    public Address() {
    }

    public Address(Long vehicleid, Double lat, Double lon, String address, String city, String state, String zip) {
        this.vehicleid = vehicleid;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Address(String address, String city, String state, String zip) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(Long vehicleid) {
        this.vehicleid = vehicleid;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
