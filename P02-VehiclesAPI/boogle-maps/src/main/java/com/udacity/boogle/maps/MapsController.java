package com.udacity.boogle.maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/maps")
public class MapsController {

    @Autowired
    private MapsRepository mapsRepository;

    @GetMapping
    public Address get(@RequestParam Long vehicleId, @RequestParam Double lat, @RequestParam Double lon) {
        Optional<Address> optionalAddress = mapsRepository.findById(vehicleId);

        if (optionalAddress.isPresent()) {
            return optionalAddress.get();
        }

        Address randomAddress = MockAddressRepository.getRandom();
        Address address = new Address(vehicleId, lat, lon,
                randomAddress.getAddress(),
                randomAddress.getCity(),
                randomAddress.getState(),
                randomAddress.getZip());
        return mapsRepository.save(address);
    }

    @DeleteMapping
    public ResponseEntity get(@RequestParam Long vehicleId) {
        mapsRepository.deleteById(vehicleId);

        return ResponseEntity.noContent().build();
    }
}
