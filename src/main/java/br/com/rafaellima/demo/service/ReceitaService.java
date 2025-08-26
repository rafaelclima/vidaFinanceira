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

@Service
@RequiredArgsConstructor
public class ReceitaService {

  private final ReceitasRepository receitasRepository;
  private final UsuarioRepository usuarioRepository;

  public Page<ListarReceitasDTO> listar(Pageable paginacao, Usuario usuario) {
    return receitasRepository.findAllBy(usuario.getId(), paginacao);
  }

  public Receita cadastrarReceita(ReceitaRequestDTO receitaRequestDTO, Usuario usuario) {
    Usuario usuarioLogado = usuarioRepository.findById(usuario.getId())
        .orElseThrow(() -> new UsuarioNotFoundException(usuario.getId()));
    Receita novaReceita = new Receita(receitaRequestDTO, usuarioLogado);
    receitasRepository.save(novaReceita);
    return novaReceita;
  }

  public Optional<ReceitaResponseDTO> buscarReceitaPorId(Long id, Usuario usuario) {
    return receitasRepository.buscarPorId(usuario.getId(), id);
  }

  public ReceitaResponseDTO atualizarReceita(Long receitaId, ReceitaUpdateRequestDTO requestDTO, Long usuarioId) {
    usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new UsuarioNotFoundException(usuarioId));

    Receita receita = receitasRepository.findById(receitaId)
        .orElseThrow(() -> new ReceitaNaoEncontradaException("Receita com id " + receitaId + " não encontrada!"));

    if (!receita.getUsuario().getId().equals(usuarioId)) {
      throw new UsuarioNaoAutenticadoException("Usuário não tem permissão para alterar esta receita");
    }

    receita.atualizarDados(requestDTO);

    return new ReceitaResponseDTO(
        receita.getId(),
        receita.getDescricao(),
        receita.getValor(),
        receita.getDataRecebimento(),
        receita.getStatus(),
        receita.getFonte());
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
