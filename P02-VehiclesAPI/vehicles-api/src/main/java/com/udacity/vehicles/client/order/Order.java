package com.udacity.vehicles.client.order;


import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Declares a class to store an order, id, quantity and vehicle id.
 */
@Embeddable
public class Order {

    @NotNull
    private Long vehicleid;

    @NotNull
    private Long quantity;

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
