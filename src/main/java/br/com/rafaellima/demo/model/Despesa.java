package br.com.rafaellima.demo.model;

import br.com.rafaellima.demo.dto.DespesaRequestDTO;
import br.com.rafaellima.demo.dto.DespesaUpdateRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Despesa")
@Table(name = "despesas")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
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
  @ToString.Exclude
  private Usuario usuario;

   public void atualizarDespesas(DespesaUpdateRequestDTO requestDTO) {
      this.descricao = requestDTO.descricao() != null ? requestDTO.descricao() : this.descricao;
      this.valor = requestDTO.valor() != null ? requestDTO.valor() : this.valor;
      this.dataDespesa = requestDTO.dataDespesa() != null ? requestDTO.dataDespesa() :
            this.dataDespesa;
      this.dataVencimento = requestDTO.dataVencimento() != null ? requestDTO.dataVencimento() :
            this.getDataVencimento();
      this.categoria = requestDTO.categoria() != null ? requestDTO.categoria() : this.categoria;
      this.metodoPagamento = requestDTO.metodoPagamento() != null ? requestDTO.metodoPagamento() :
            this.metodoPagamento;
      this.status = requestDTO.status() != null ? requestDTO.status() : this.status;
   }

}
