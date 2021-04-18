package com.udacity.boogle.maps;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapsRepository extends CrudRepository<Address, Long> {
}
