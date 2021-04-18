package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.order.Order;
import com.udacity.vehicles.client.order.OrdersClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final MapsClient mapsClient;
    private final PriceClient priceClient;
    private final OrdersClient ordersClient;

    public CarService(CarRepository repository,
                      MapsClient mapsClient,
                      PriceClient priceClient,
                      OrdersClient ordersClient
    ) {
        this.repository = repository;
        this.mapsClient = mapsClient;
        this.priceClient = priceClient;
        this.ordersClient = ordersClient;
    }

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository
                .findAll()
                .stream()
                .peek(car -> car.setPrice(priceClient.getPrice(car.getId())))
                .collect(Collectors.toList());
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Optional<Car> optionalCar = repository.findById(id);
        Car car = optionalCar.orElseThrow(CarNotFoundException::new);

        String price = priceClient.getPrice(id);
        car.setPrice(price);

        Location location = mapsClient.getAddress(car.getId(), car.getLocation());
        car.setLocation(location);

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the com.udacity.repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            String price = priceClient.getPrice(car.getId());
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        carToBeUpdated.setPrice(price);
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        Car savedCar = repository.save(car);
        String price = priceClient.getPrice(car.getId());
        savedCar.setPrice(price);
        return savedCar;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param car A car object, which can be either new or existing
     * @param id  the ID number of the car to gather information on
     * @return the new/updated car is stored in the com.udacity.repository
     */
    public Car save(Long id, Car car) {
        if (car.getId() != null) {
            String price = priceClient.getPrice(car.getId());
            return repository.findById(id)
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        carToBeUpdated.setPrice(price);
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        Car savedCar = repository.save(car);
        String price = priceClient.getPrice(car.getId());
        savedCar.setPrice(price);
        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Optional<Car> optionalCar = repository.findById(id);
        Car car = optionalCar.orElseThrow(CarNotFoundException::new);
        repository.deleteById(car.getId());
        priceClient.deletePrice(car.getId());
        mapsClient.deleteAddress(car.getId());
    }


    /**
     * Make an order given car ID
     *
     * @param order contains vehicle id and quantity
     */
    public Order makeOrder(Order order) {
        Optional<Car> optionalCar = repository.findById(order.getVehicleid());
        optionalCar.orElseThrow(CarNotFoundException::new);
        return ordersClient.postOrder(order);
    }
}
