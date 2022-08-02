package org.aibles.failwall.document.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class DocumentReqDto {

    private String title;
    private String content;
    private Set<Integer> categoryIds;
    private String thumbnail;
}
