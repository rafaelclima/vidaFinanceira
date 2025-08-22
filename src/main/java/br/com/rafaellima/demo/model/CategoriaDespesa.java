package br.com.rafaellima.demo.model;

public enum CategoriaDespesa {
  ALIMENTACAO("Alimentação"),
  MORADIA("Moradia"),
  TRANSPORTE("Transporte"),
  SAUDE("Saúde"),
  LAZER("Lazer"),
  EDUCACAO("Educação"),
  CONTAS("Contas e Serviços"),
  COMPRAS("Compras"),
  OUTROS("Outros");

  private final String nomeCategoria;

  CategoriaDespesa(String nomeCategoria) {
    this.nomeCategoria = nomeCategoria;
  }

  public String getNomeCategoria() {
    return nomeCategoria;
  }

}
