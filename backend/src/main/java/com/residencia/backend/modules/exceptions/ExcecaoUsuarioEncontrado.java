package com.residencia.backend.modules.exceptions;

public class ExcecaoUsuarioEncontrado extends RuntimeException{
  public ExcecaoUsuarioEncontrado(){
    super("Usuário já existe");
  }
}

