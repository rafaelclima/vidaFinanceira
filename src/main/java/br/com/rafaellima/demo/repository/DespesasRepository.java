package br.com.rafaellima.demo.repository;

import br.com.rafaellima.demo.dto.DespesaResponseDTO;
import br.com.rafaellima.demo.dto.ListarDespesasDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rafaellima.demo.model.Despesa;

import java.util.Optional;

@Repository
public interface DespesasRepository extends JpaRepository<Despesa, Long> {
   @Query("""
          SELECT new br.com.rafaellima.demo.dto.ListarDespesasDTO(
              d.id,
              d.descricao,
              d.valor,
              d.dataVencimento,
              d.status
          ) FROM Despesa d
          WHERE d.usuario.id = :userId
          ORDER BY d.id DESC
      """)
   Page<ListarDespesasDTO> findAllBy(@Param("userId") Long userId, Pageable paginacao);

   Optional<Despesa> findByIdAndUsuarioId(Long despesaId, Long usuarioId);

}
