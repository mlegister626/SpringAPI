package com.codewithmosh.repositories;

import com.codewithmosh.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}