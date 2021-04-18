package com.udacity.orderservice.repository;

import com.udacity.orderservice.entity.Ordering;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Ordering, Long> {
}
