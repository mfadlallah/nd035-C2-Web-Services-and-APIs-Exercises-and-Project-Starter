package com.udacity.orderservice.repository;

import com.udacity.orderservice.entity.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RepositoryRestController
@BasePathAwareController
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @PostMapping(path = "/orderings/{vehicleId}/{quantity}")
    public ResponseEntity<?> postOrder(@PathVariable Long vehicleId, @PathVariable Long quantity) {
        Ordering ordering = new Ordering(vehicleId, quantity);
        return ResponseEntity.ok(repository.save(ordering));
    }
}