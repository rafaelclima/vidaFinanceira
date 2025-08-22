package br.com.rafaellima.demo.model;

public enum FonteReceita {
  SALARIO("Salário"),
  FREELANCE("Trabalho Freelance"),
  VENDAS("Vendas"),
  INVESTIMENTOS("Rendimento de Investimentos"),
  ALUGUEL("Aluguel Recebido"),
  PRESENTE("Presente"),
  REEMBOLSO("Reembolso"),
  OUTROS("Outros");

  private final String displayName;

  FonteReceita(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
