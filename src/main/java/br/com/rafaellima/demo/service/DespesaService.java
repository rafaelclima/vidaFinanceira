package br.com.rafaellima.demo.service;

import br.com.rafaellima.demo.dto.DespesaRequestDTO;
import br.com.rafaellima.demo.dto.DespesaResponseDTO;
import br.com.rafaellima.demo.exception.UsuarioNaoAutenticadoException;
import br.com.rafaellima.demo.model.Despesa;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.repository.DespesasRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DespesaService {
   private final DespesasRepository despesasRepository;

   public Page<DespesaResponseDTO> listar(Pageable paginacao, Usuario usuarioLogado) {
      if (usuarioLogado == null) {
         throw new UsuarioNaoAutenticadoException("Usuário não autenticado! Por favor, faça login.");
      }
      Long userId = usuarioLogado.getId();
      return despesasRepository.findAllBy(userId, paginacao);
   }

   @Transactional
   public Despesa criar(DespesaRequestDTO requestDTO, Usuario usuarioLogado) {
      if (usuarioLogado == null) {
         throw new UsuarioNaoAutenticadoException("Usuário não autenticado! Por favor, faça login");
      }
      var despesa = new Despesa(requestDTO, usuarioLogado);
      despesasRepository.save(despesa);
      return despesa;
   }
}
