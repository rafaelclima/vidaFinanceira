package br.com.rafaellima.demo.model;

public enum StatusDespesa {
  PAGA("paga"),
  PENDENTE("pendente"),
  ATRASADA("atrasada");

  private final String statusDespesa;

  StatusDespesa(String statusDespesa) {
    this.statusDespesa = statusDespesa;
  }

  public String getStatusDespesa() {
    return statusDespesa;
  }

}
