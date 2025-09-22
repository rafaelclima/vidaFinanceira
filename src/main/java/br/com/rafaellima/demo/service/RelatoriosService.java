package br.com.rafaellima.demo.service;

import br.com.rafaellima.demo.dto.ResumoMensalDTO;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.repository.DespesasRepository;
import br.com.rafaellima.demo.repository.ReceitasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RelatoriosService {

   private final ReceitasRepository receitasRepository;
   private final DespesasRepository despesasRepository;

   public ResumoMensalDTO gerarRelatorioMensal(int ano, int mes, Usuario usuarioLogado) {

      LocalDate dataInicio = LocalDate.of(ano, mes, 1);
      LocalDate dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

      BigDecimal totalReceitas =
            receitasRepository.calcularTotalReceitasNoPeriodo(usuarioLogado.getId(), dataInicio, dataFim);
      BigDecimal totalDespesas =
            despesasRepository.calcularTotalDespesasNoPeriodo(usuarioLogado.getId(), dataInicio, dataFim);
      BigDecimal saldoFinal = totalReceitas.subtract(totalDespesas);
      return new ResumoMensalDTO(totalReceitas, totalDespesas, saldoFinal);
   }
}
