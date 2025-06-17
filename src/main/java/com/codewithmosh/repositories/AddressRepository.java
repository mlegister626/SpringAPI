package com.codewithmosh.repositories;

import com.codewithmosh.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}