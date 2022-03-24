package org.aibles.failwall.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResDto {
    private String name;
    private String email;
    private boolean isActivated;
}
