package br.com.rafaellima.demo.exception;

public class UsuarioNotFoundException extends RuntimeException {
  public UsuarioNotFoundException(String message) {
    super(message);
  }

  public UsuarioNotFoundException(Long id) {
    super(String.format("Usuário não encontrado com ID: %d", id));
  }
}
