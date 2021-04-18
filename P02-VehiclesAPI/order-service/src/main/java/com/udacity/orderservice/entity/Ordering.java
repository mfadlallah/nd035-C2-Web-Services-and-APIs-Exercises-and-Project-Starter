package com.udacity.orderservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ordering {
    @Id
    private Long vehicleid;

    private Long quantity;

    public Ordering() {
    }

    public Ordering(Long vehicleid, Long quantity) {
        this.vehicleid = vehicleid;
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(Long vehicleid) {
        this.vehicleid = vehicleid;
    }
}
