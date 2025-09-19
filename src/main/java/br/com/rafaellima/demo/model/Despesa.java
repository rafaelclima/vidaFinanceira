package br.com.rafaellima.demo.model;

import br.com.rafaellima.demo.dto.DespesaRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Despesa")
@Table(name = "despesas")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
  @ToString.Exclude
  private Usuario usuario;

   public Despesa(DespesaRequestDTO requestDTO, Usuario usuarioLogado) {

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
      Despesa despesa = (Despesa) o;
      return getId() != null && Objects.equals(getId(), despesa.getId());
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
