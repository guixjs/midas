package com.residencia.backend.modules.services.usuario;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.residencia.backend.modules.dto.usuario.AuthUsuarioDTO;
import com.residencia.backend.modules.dto.usuario.AuthUsuarioResponseDTO;
import com.residencia.backend.modules.repositories.UsuarioRepository;
import com.residencia.backend.modules.services.conta.CriarContaGeralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
public class AutenticarUsuarioService {

  @Value("${security.token.secret.usuario}")
  private String secretKey;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private CriarContaGeralService criarContaGeralService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthUsuarioResponseDTO execute(AuthUsuarioDTO authUsuarioDTO) throws AuthenticationException {
    var usuario = this.usuarioRepository.findByCpf(authUsuarioDTO.getCpf())
        .orElseThrow(()->{
          throw new UsernameNotFoundException("Usuario ou senha inválida");
        });



    var verificaSenha = passwordEncoder.matches(authUsuarioDTO.getSenha(), usuario.getSenha());

    if(!verificaSenha){
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expires_in = Instant.now().plus(Duration.ofHours(4));
    var token = JWT.create()
        .withIssuer("midas")
        .withSubject(usuario.getId().toString())
        .withExpiresAt(expires_in)
        .sign(algorithm);

    criarContaGeralService.criarContaGeral(usuario);
    return AuthUsuarioResponseDTO.builder()
    .token(token)
    .expires_in(expires_in.toEpochMilli())
    .build();
  }
}
