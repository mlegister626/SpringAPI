package com.codewithmosh.repositories.repositories;

import com.codewithmosh.entities.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}