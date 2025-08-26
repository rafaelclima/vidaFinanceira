package br.com.rafaellima.demo.exception;

public class ReceitaNaoEncontradaException extends RuntimeException {

  public ReceitaNaoEncontradaException(String message) {
    super(message);
  }

  public ReceitaNaoEncontradaException(Long id) {
    super(String.format("Receita não encontrada com ID: %d", id));
  }

}
