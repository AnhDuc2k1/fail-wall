package org.aibles.failwall.document.repository;

import org.aibles.failwall.document.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    Set<Category> findAllByIdIn(Set<Integer> categoryIds);
}
