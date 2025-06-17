package com.codewithmosh.repositories;

import com.codewithmosh.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}