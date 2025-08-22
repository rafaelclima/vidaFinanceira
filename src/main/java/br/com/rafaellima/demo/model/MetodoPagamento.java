package br.com.rafaellima.demo.model;

public enum MetodoPagamento {
  CARTAO_CREDITO("Cartão de Crédito"),
  CARTAO_DEBITO("Cartão de Débito"),
  PIX("PIX"),
  DINHEIRO("Dinheiro"),
  TRANSFERENCIA_BANCARIA("Transferência Bancária (TED/DOC)"),
  BOLETO("Boleto");

  private final String metodoPagamento;

  MetodoPagamento(String metodoPagamento) {
    this.metodoPagamento = metodoPagamento;
  }

  public String getMetodoPagamento() {
    return metodoPagamento;
  }

}
