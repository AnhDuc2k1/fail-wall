package org.aibles.failwall.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "roles")
@Data
public class Role {

    @Id
    private Long id;
    private String name;

}
