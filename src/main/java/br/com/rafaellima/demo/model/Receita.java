package br.com.rafaellima.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.rafaellima.demo.dto.ReceitaRequestDTO;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Receita")
@Table(name = "receitas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Receita {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String descricao;

  @Column(nullable = false)
  private BigDecimal valor;

  @Column(nullable = false)
  private LocalDate dataRecebimento;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StatusReceita status;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FonteReceita fonte;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  public Receita(ReceitaRequestDTO receitaRequestDTO, Usuario usuario) {
    this.descricao = receitaRequestDTO.descricao();
    this.valor = receitaRequestDTO.valor();
    this.dataRecebimento = receitaRequestDTO.dataRecebimento();
    this.status = receitaRequestDTO.status();
    this.fonte = receitaRequestDTO.fonte();
    this.usuario = usuario;
  }

}
