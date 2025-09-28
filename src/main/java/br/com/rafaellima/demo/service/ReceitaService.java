package br.com.rafaellima.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.rafaellima.demo.dto.ListarReceitasDTO;
import br.com.rafaellima.demo.dto.ReceitaRequestDTO;
import br.com.rafaellima.demo.dto.ReceitaResponseDTO;
import br.com.rafaellima.demo.dto.ReceitaUpdateRequestDTO;
import br.com.rafaellima.demo.exception.ReceitaNaoEncontradaException;
import br.com.rafaellima.demo.exception.UsuarioNaoAutenticadoException;
import br.com.rafaellima.demo.exception.UsuarioNotFoundException;
import br.com.rafaellima.demo.model.Receita;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.repository.ReceitasRepository;
import br.com.rafaellima.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceitaService {

  private final ReceitasRepository receitasRepository;
  private final UsuarioRepository usuarioRepository;

  public Page<ListarReceitasDTO> listar(Pageable paginacao, Usuario usuario) {
    return receitasRepository.findAllBy(usuario.getId(), paginacao);
  }

  @Transactional
  public Receita cadastrarReceita(ReceitaRequestDTO receitaRequestDTO, Usuario usuario) {
    Usuario usuarioLogado = usuarioRepository.findById(usuario.getId())
        .orElseThrow(() -> new UsuarioNotFoundException(usuario.getId()));
    
    Receita novaReceita = new Receita();
    novaReceita.setDescricao(receitaRequestDTO.descricao());
    novaReceita.setValor(receitaRequestDTO.valor());
    novaReceita.setDataRecebimento(receitaRequestDTO.dataRecebimento());
    novaReceita.setStatus(receitaRequestDTO.status());
    novaReceita.setFonte(receitaRequestDTO.fonte());
    novaReceita.setUsuario(usuarioLogado);

    receitasRepository.save(novaReceita);
    return novaReceita;
  }

  public Receita detalhar(Long id, Usuario usuario) {
    return receitasRepository.findByIdAndUsuarioId(id, usuario.getId())
        .orElseThrow(() -> new ReceitaNaoEncontradaException("Receita com id " + id + " não encontrada!"));
  }

  @Transactional
  public Receita atualizarReceita(Long receitaId, ReceitaUpdateRequestDTO requestDTO, Usuario usuario) {
    Receita receita = receitasRepository.findByIdAndUsuarioId(receitaId, usuario.getId())
        .orElseThrow(() -> new ReceitaNaoEncontradaException("Receita com id " + receitaId + " não encontrada!"));

    receita.atualizarDados(requestDTO);

    return receita;
  }

  @Transactional
  public void deletarReceita(Long receitaId, Usuario usuario) {
    Receita receita = receitasRepository.findByIdAndUsuarioId(receitaId, usuario.getId())
        .orElseThrow(() -> new ReceitaNaoEncontradaException("Receita com id " + receitaId + " não encontrada!"));

    receitasRepository.delete(receita);
  }

  // Método para pegar o id do usuário autenticado.
  // Substituído pelo método moderno do spring @AuthenticationPrincipal
  /*
   * private Usuario getUsuarioLogado() {
   * try {
   * return (Usuario) SecurityContextHolder.getContext()
   * .getAuthentication()
   * .getPrincipal();
   * } catch (Exception e) {
   * throw new UsuarioNaoAutenticadoException("Usuário não autenticado!");
   * }
   * }
   *
   * private Long getUsuarioLogadoId() {
   * return getUsuarioLogado().getId();
   * }
   *
   */

}
