package br.com.rafaellima.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @Column(nullable = false)
  private String nome;

  @Setter
  @Column(nullable = false)
  private String email;

  @Setter
  @Column(nullable = false)
  private String senha;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Receita> receitas;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Despesa> despesas;

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; // conta nunca expira
  }

  @Override
  public boolean isAccountNonLocked() {
    return true; // conta nunca fica bloqueada
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; // credenciais nunca expiram
  }

  @Override
  public boolean isEnabled() {
    return true; // usuário sempre está ativo
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
      Usuario usuario = (Usuario) o;
      return getId() != null && Objects.equals(getId(), usuario.getId());
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
