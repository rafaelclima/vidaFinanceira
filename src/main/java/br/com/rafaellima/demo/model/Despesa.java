package br.com.rafaellima.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Despesa")
@Table(name = "despesas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Despesa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String descricao;

  @Column(nullable = false)
  private BigDecimal valor;

  @Column(nullable = false)
  private LocalDate dataDespesa;

  private LocalDate dataVencimento;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CategoriaDespesa categoria;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MetodoPagamento metodoPagamento;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StatusDespesa status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

}
