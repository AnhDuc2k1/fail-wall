package org.aibles.failwall.document.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aibles.failwall.document.model.Category;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicDocumentResDto {
    private long id;
    private String title;
    private String thumbnail;
    private Set<Category> categories;
}
