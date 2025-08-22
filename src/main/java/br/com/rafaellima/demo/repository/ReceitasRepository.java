package br.com.rafaellima.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rafaellima.demo.dto.ListarReceitasDTO;
import br.com.rafaellima.demo.model.Receita;

@Repository
public interface ReceitasRepository extends JpaRepository<Receita, Long> {

  @Query("""
          SELECT new br.com.rafaellima.demo.dto.ListarReceitasDTO(
              r.descricao,
              r.valor,
              r.dataRecebimento,
              r.status,
              r.fonte
          ) FROM Receita r
          WHERE r.usuario.id = :userId
          ORDER BY r.dataRecebimento DESC
      """)
  Page<ListarReceitasDTO> findAllBy(@Param("userId") Long userId, Pageable paginacao);

}
