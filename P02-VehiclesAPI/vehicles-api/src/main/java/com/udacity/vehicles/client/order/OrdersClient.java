package com.udacity.vehicles.client.order;

import com.udacity.vehicles.client.maps.Address;
import com.udacity.vehicles.domain.Location;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * Implements a class to interface with the Maps Client for location data.
 */
@Component
public class OrdersClient {

    private static final Logger log = LoggerFactory.getLogger(OrdersClient.class);

    private final WebClient client;
    private final ModelMapper mapper;

    public OrdersClient(WebClient ordering,
                        ModelMapper mapper) {
        this.client = ordering;
        this.mapper = mapper;
    }

    /**
     * Gets an order for a specific vehicle id.
     *
     * @param order contains vehicleId and quantity.
     * @return An Order,
     * or an exception message noting the Maps service is down
     */
    public Order postOrder(Order order) {
        try {
            return client
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/orderings/" + order.getVehicleid() + "/" + order.getQuantity())
                            .build()
                    )
                    .retrieve().bodyToMono(Order.class).block();
        } catch (Exception e) {
            log.warn("Ordering service is down");
            return null;
        }
    }
}
