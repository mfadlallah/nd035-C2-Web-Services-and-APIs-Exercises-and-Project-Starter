package com.udacity.vehicles.client.maps;

import com.udacity.vehicles.domain.Location;
import java.util.Objects;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implements a class to interface with the Maps Client for location data.
 */
@Component
public class MapsClient {

    private static final Logger log = LoggerFactory.getLogger(MapsClient.class);

    private final WebClient client;
    private final ModelMapper mapper;

    public MapsClient(WebClient maps,
            ModelMapper mapper) {
        this.client = maps;
        this.mapper = mapper;
    }

    /**
     * Gets an address from the Maps client, given latitude and longitude.
     * @param location An object containing "lat" and "lon" of location
     * @return An updated location including street, city, state and zip,
     *   or an exception message noting the Maps service is down
     */
    public Location getAddress(Long vehicleId, Location location) {
        try {
            Address address = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/maps/")
                            .queryParam("vehicleId", vehicleId)
                            .queryParam("lat", location.getLat())
                            .queryParam("lon", location.getLon())
                            .build()
                    )
                    .retrieve().bodyToMono(Address.class).block();

            mapper.map(Objects.requireNonNull(address), location);

            return location;
        } catch (Exception e) {
            log.warn("Map service is down");
            return location;
        }
    }

    /**
     * Deletes a vehicle location from the maps client, given vehicle ID.
     *
     * @param vehicleId ID number of the vehicle for which to get the price
     * error message that the vehicle ID is invalid, or note that the
     * service is down.
     */
    public void deleteAddress(Long vehicleId) {
        client.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/maps/")
                        .queryParam("vehicleId", vehicleId)
                        .build()
                )
                .retrieve()
                .bodyToMono(Void.class).block();
    }
}
