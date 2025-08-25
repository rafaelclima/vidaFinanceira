package br.com.rafaellima.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UsuarioNaoAutenticadoException extends RuntimeException {
  public UsuarioNaoAutenticadoException(String message) {
    super(message);
  }
}
