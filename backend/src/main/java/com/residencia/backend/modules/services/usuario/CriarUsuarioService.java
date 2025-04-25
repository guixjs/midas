package com.residencia.backend.modules.services.usuario;


import com.residencia.backend.modules.dto.usuario.UsuarioDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseDTO;
import com.residencia.backend.modules.exceptions.ExcecaoUsuarioEncontrado;
import com.residencia.backend.modules.mapper.UsuarioMapper;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CriarUsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;


  public UsuarioResponseDTO execute(UsuarioDTO usuarioDTO){

    this.usuarioRepository.findByCpfOrEmail(usuarioDTO.getCpf(), usuarioDTO.getEmail())
        .ifPresent((user)->{
          throw new ExcecaoUsuarioEncontrado();
        });
    var senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
    usuarioDTO.setSenha(senhaCriptografada);

    UsuarioEntity usuario = UsuarioMapper.toEntity(usuarioDTO);
    UsuarioEntity resultado = this.usuarioRepository.save(usuario);
    return UsuarioMapper.toResponseDTO(resultado);
  }
}
