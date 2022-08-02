package org.aibles.failwall.document.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("category_document")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentCategory {

    private int categoryId;
}
