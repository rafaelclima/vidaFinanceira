package br.com.rafaellima.demo.exception;

public class DespesaNaoEncontradaException extends RuntimeException{

   public DespesaNaoEncontradaException(String message) {
      super(message);
   }

   public DespesaNaoEncontradaException(Long id) {
      super(String.format("Despesa não encontrada com ID: %d", id));
   }


}
