package br.com.rafaellima.demo.service;

import br.com.rafaellima.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

   private final UsuarioRepository usuarioRepository;

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
   }
}
