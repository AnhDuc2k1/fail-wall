package org.aibles.failwall.document.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryReqDto {

    @NotNull(message = "category's name cannot be null")
    private String name;
}
