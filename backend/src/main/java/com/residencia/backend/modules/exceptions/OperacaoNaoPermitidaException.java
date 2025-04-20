package com.residencia.backend.modules.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException {
  public OperacaoNaoPermitidaException(String message) {
    super(message);
  }
}
