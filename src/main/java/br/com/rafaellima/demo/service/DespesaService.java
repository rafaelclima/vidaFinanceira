package br.com.rafaellima.demo.service;

import br.com.rafaellima.demo.dto.DespesaRequestDTO;
import br.com.rafaellima.demo.dto.DespesaResponseDTO;
import br.com.rafaellima.demo.dto.DespesaUpdateRequestDTO;
import br.com.rafaellima.demo.dto.ListarDespesasDTO;
import br.com.rafaellima.demo.exception.DespesaNaoEncontradaException;
import br.com.rafaellima.demo.exception.UsuarioNaoAutenticadoException;
import br.com.rafaellima.demo.model.Despesa;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.repository.DespesasRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DespesaService {
   private final DespesasRepository despesasRepository;

   public Page<ListarDespesasDTO> listar(Pageable paginacao, Usuario usuarioLogado) {
      if (usuarioLogado == null) {
         throw new UsuarioNaoAutenticadoException("Usuário não autenticado! Por favor, faça login.");
      }
      Long userId = usuarioLogado.getId();
      return despesasRepository.findAllBy(userId, paginacao);
   }

   public Despesa criar(DespesaRequestDTO requestDTO, Usuario usuarioLogado) {
      if (usuarioLogado == null) {
         throw new UsuarioNaoAutenticadoException("Usuário não autenticado! Por favor, faça login");
      }
      var despesa = new Despesa();
      despesa.setDescricao(requestDTO.descricao());
      despesa.setValor(requestDTO.valor());
      despesa.setDataDespesa(requestDTO.dataDespesa());
      despesa.setDataVencimento(requestDTO.dataVencimento());
      despesa.setCategoria(requestDTO.categoria());
      despesa.setMetodoPagamento(requestDTO.metodoPagamento());
      despesa.setStatus(requestDTO.status());
      despesa.setUsuario(usuarioLogado);

      despesasRepository.save(despesa);
      return despesa;
   }

   public ListarDespesasDTO detalhar(Long id, Usuario usuarioLogado) {

      Despesa despesaBuscada = despesasRepository.findByIdAndUsuarioId(id,
            usuarioLogado.getId()).orElseThrow(() -> new DespesaNaoEncontradaException(id));

      return new ListarDespesasDTO(
            despesaBuscada.getId(),
            despesaBuscada.getDescricao(),
            despesaBuscada.getValor(),
            despesaBuscada.getDataVencimento(),
            despesaBuscada.getStatus()
      );

   }

   @Transactional
   public Despesa atualizar(Long id, DespesaUpdateRequestDTO requestDTO,
                                            Usuario usuarioLogado) {
      Despesa despesaBuscada = despesasRepository.findByIdAndUsuarioId(id,
            usuarioLogado.getId()).orElseThrow(() -> new DespesaNaoEncontradaException(id));

      despesaBuscada.atualizarDespesas(requestDTO);

      return despesaBuscada;
   }

   @Transactional
   public void deletar(Long id, Usuario usuarioLogado) {
      Despesa despesaBuscada =
            despesasRepository.findByIdAndUsuarioId(id, usuarioLogado.getId()).orElseThrow(() -> new DespesaNaoEncontradaException(id));
      despesasRepository.delete(despesaBuscada);
   }

}
