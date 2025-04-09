package com.residencia.backend.modules.services.usuario;


import com.residencia.backend.modules.exceptions.ExcecaoUsuarioEncontrado;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CriarUsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;


  public UsuarioEntity execute(UsuarioEntity usuarioEntity){
    this.usuarioRepository.findByCpfOrEmail(usuarioEntity.getCpf(), usuarioEntity.getEmail())
        .ifPresent((user)->{
          throw new ExcecaoUsuarioEncontrado();
        });
    return this.usuarioRepository.save(usuarioEntity);
  }

}
