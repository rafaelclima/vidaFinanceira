package br.com.rafaellima.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.rafaellima.demo.dto.ListarReceitasDTO;
import br.com.rafaellima.demo.dto.ReceitaRequestDTO;
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

  public Page<ListarReceitasDTO> listar(Pageable paginacao) {
    Long userId = getUsuarioLogadoId();
    return receitasRepository.findAllBy(userId, paginacao);
  }

  public Receita cadastrarReceita(ReceitaRequestDTO receitaRequestDTO) {
    Long userId = getUsuarioLogadoId();
    Usuario usuarioLogado = usuarioRepository.findById(userId).orElseThrow(() -> new UsuarioNotFoundException(userId));
    Receita novaReceita = new Receita(receitaRequestDTO, usuarioLogado);
    receitasRepository.save(novaReceita);
    return novaReceita;
  }

  private Usuario getUsuarioLogado() {
    try {
      return (Usuario) SecurityContextHolder.getContext()
          .getAuthentication()
          .getPrincipal();
    } catch (Exception e) {
      throw new RuntimeException("Usuário não autenticado");
    }
  }

  private Long getUsuarioLogadoId() {
    return getUsuarioLogado().getId();
  }

}
