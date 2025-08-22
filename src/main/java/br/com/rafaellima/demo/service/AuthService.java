package br.com.rafaellima.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rafaellima.demo.dto.RegistroDTO;
import br.com.rafaellima.demo.exception.EmailAlreadyExistsException;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.repository.UsuarioRepository;
import br.com.rafaellima.demo.utils.JwtUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UsuarioRepository usuarioRepository;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  public String login(String email, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    return jwtUtils.generateToken(usuario);
  }

  public String register(RegistroDTO registroDTO) {

    if (usuarioRepository.findByEmail(registroDTO.email()).isPresent()) {
      throw new EmailAlreadyExistsException("Este email já está em uso.");
    }

    Usuario novoUsuario = new Usuario();
    novoUsuario.setNome(registroDTO.nome());
    novoUsuario.setEmail(registroDTO.email());
    novoUsuario.setSenha(passwordEncoder.encode(registroDTO.senha()));

    Usuario novoUsuarioSalvo = usuarioRepository.save(novoUsuario);

    return jwtUtils.generateToken(novoUsuarioSalvo);

  }

}
