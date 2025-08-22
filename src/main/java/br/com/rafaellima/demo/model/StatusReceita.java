package br.com.rafaellima.demo.model;

public enum StatusReceita {
  RECEBIDA("recebida"),
  A_RECEBER("a receber");

  private final String displayName;

  StatusReceita(String displayName) {
    this.displayName = displayName;
  }

  public String getStatusReceita() {
    return displayName;
  }

}
