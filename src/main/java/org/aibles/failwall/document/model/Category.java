package org.aibles.failwall.document.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private int id;

    @NotNull(message = "category's name cannot be null")
    private String name;

}