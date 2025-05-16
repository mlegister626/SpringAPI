package com.codewithmosh.repositories.repositories;

import com.codewithmosh.entities.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}