package br.com.rafaellima.demo.repository;

import br.com.rafaellima.demo.dto.DespesaPorCategoriaDTO;
import br.com.rafaellima.demo.dto.ListarDespesasDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rafaellima.demo.model.Despesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

   @Query("""
        SELECT COALESCE(SUM(d.valor), 0.0)
         FROM Despesa d
         WHERE d.usuario.id = :userId
         AND d.dataDespesa BETWEEN :dataInicio AND :dataFim
        """)
   BigDecimal calcularTotalDespesasNoPeriodo(
         @Param("userId") Long userId,
         @Param("dataInicio") java.time.LocalDate dataInicio,
         @Param("dataFim") java.time.LocalDate dataFim
   );

   @Query("""
         SELECT new br.com.rafaellima.demo.dto.DespesaPorCategoriaDTO(
             d.categoria,
             COALESCE(SUM(d.valor), 0.0)
         )
         FROM Despesa d
         WHERE d.usuario.id = :usuarioId
         AND d.dataDespesa BETWEEN :dataInicio AND :dataFim
         GROUP BY d.categoria
         ORDER BY SUM(d.valor) DESC
         """)
   List<DespesaPorCategoriaDTO> calcularDespesasPorCategoria(Long usuarioId,
                                                             LocalDate dataInicio,
                                                             LocalDate dataFim);

}
