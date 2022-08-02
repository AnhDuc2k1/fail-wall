package org.aibles.failwall.document.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailDocumentResDto extends BasicDocumentResDto{

    private String content;
    private long views;
    private String createdBy;
    private String lastUpdatedBy;
    private Instant createdAt;
    private Instant lastUpdatedAt;
}
