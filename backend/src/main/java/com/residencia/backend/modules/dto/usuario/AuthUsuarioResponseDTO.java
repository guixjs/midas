package com.residencia.backend.modules.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUsuarioResponseDTO {
    private String token;
    private Long expires_in;
}
