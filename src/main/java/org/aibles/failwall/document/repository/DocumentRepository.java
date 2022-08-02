package org.aibles.failwall.document.repository;

import org.aibles.failwall.document.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {

    Page<Document> findAll(Pageable pageable);


}
