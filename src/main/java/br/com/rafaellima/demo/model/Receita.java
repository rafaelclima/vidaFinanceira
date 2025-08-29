package br.com.rafaellima.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.rafaellima.demo.dto.ReceitaRequestDTO;
import br.com.rafaellima.demo.dto.ReceitaUpdateRequestDTO;
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

@Entity(name = "Receita")
@Table(name = "receitas")
@Data
@NoArgsConstructor
@AllArgsConstructor

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

  public void atualizarDados(ReceitaUpdateRequestDTO requestDTO) {
    this.descricao = requestDTO.descricao() != null ? requestDTO.descricao() : this.descricao;
    this.valor = requestDTO.valor() != null ? requestDTO.valor() : this.valor;
    this.dataRecebimento = requestDTO.dataRecebimento() != null ? requestDTO.dataRecebimento() : this.dataRecebimento;
    this.status = requestDTO.status() != null ? requestDTO.status() : this.status;
    this.fonte = requestDTO.fonte() != null ? requestDTO.fonte() : this.fonte;
  }

}
