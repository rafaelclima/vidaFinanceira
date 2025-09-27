package br.com.rafaellima.demo.model;

import br.com.rafaellima.demo.dto.ReceitaRequestDTO;
import br.com.rafaellima.demo.dto.ReceitaUpdateRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Receita")
@Table(name = "receitas")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
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
  @ToString.Exclude
  private Usuario usuario;

  public void atualizarDados(ReceitaUpdateRequestDTO requestDTO) {
    this.descricao = requestDTO.descricao() != null ? requestDTO.descricao() : this.descricao;
    this.valor = requestDTO.valor() != null ? requestDTO.valor() : this.valor;
    this.dataRecebimento = requestDTO.dataRecebimento() != null ? requestDTO.dataRecebimento() : this.dataRecebimento;
    this.status = requestDTO.status() != null ? requestDTO.status() : this.status;
    this.fonte = requestDTO.fonte() != null ? requestDTO.fonte() : this.fonte;
  }

}
