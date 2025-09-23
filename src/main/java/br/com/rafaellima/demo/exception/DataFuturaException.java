package br.com.rafaellima.demo.exception;

public class DataFuturaException extends RuntimeException {
   public DataFuturaException(String message) {
      super(message);
   }

   public DataFuturaException() {
      super("Nào é possível utilizar uma data futura.");
   }
}
