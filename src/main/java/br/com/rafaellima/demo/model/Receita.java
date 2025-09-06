package br.com.rafaellima.demo.model;

import br.com.rafaellima.demo.dto.ReceitaRequestDTO;
import br.com.rafaellima.demo.dto.ReceitaUpdateRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Receita")
@Table(name = "receitas")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

   @Override
   public final boolean equals(Object o) {

      if (this == o) return true;
      if (o == null) return false;
      Class<?> oEffectiveClass = o instanceof HibernateProxy ?
            ((HibernateProxy) o)
                  .getHibernateLazyInitializer()
                  .getPersistentClass() :
            o.getClass();
      Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
            ((HibernateProxy) this)
                  .getHibernateLazyInitializer()
                  .getPersistentClass() :
            this.getClass();
      if (thisEffectiveClass != oEffectiveClass) return false;
      Receita receita = (Receita) o;
      return getId() != null && Objects.equals(getId(), receita.getId());
   }

   @Override
   public final int hashCode() {

      return this instanceof HibernateProxy ?
            ((HibernateProxy) this)
                  .getHibernateLazyInitializer()
                  .getPersistentClass()
                  .hashCode() :
            getClass().hashCode();
   }
}
