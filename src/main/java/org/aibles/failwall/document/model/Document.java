package org.aibles.failwall.document.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Document {

    @Id
    private long id;
    private String title;
    private String content;
    private String thumbnail;
    private long views;
    private String createdBy;
    private String lastUpdatedBy;
    private Instant createdAt;
    private Instant lastUpdatedAt;

    @MappedCollection(idColumn = "document_id")
    private Set<DocumentCategory> documentCategories = new HashSet<>();

}
