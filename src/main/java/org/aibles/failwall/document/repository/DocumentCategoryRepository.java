package org.aibles.failwall.document.repository;

import org.aibles.failwall.document.model.Category;
import org.aibles.failwall.document.model.DocumentCategory;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DocumentCategoryRepository extends CrudRepository<DocumentCategory, Integer> {

    @Query("select c.id, c.name from category c inner join category_document cd " +
            "where c.id = cd.category_id and cd.document_id = :documentId")
    Set<Category> getAllCategoriesByDocumentId(@Param("documentId") long documentId);
}
