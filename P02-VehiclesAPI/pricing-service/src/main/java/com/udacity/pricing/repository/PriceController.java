package com.udacity.pricing.repository;

import com.udacity.pricing.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RepositoryRestController
@BasePathAwareController
public class PriceController {

    @Autowired
    private PriceRepository repository;

    @GetMapping(path = "/prices/{vehicleId}")
    public ResponseEntity<?> getPrice(@PathVariable Long vehicleId) {
        Optional<Price> optionalPrice = repository.findById(vehicleId);
        if (optionalPrice.isPresent()) {
            return ResponseEntity.ok(optionalPrice.get());
        }

        Price price = new Price(getCurrency(), randomPrice(), vehicleId);
        return ResponseEntity.ok(repository.save(price));
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

    private String getCurrency() {
        return "USD";
    }

}