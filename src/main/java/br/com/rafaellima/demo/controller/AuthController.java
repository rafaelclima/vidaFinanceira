package br.com.rafaellima.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaellima.demo.dto.AuthResponseDTO;
import br.com.rafaellima.demo.dto.LoginDTO;
import br.com.rafaellima.demo.dto.RegistroDTO;
import br.com.rafaellima.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/registro")
  public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegistroDTO registroDTO) {
    String token = authService.register(registroDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(token, "Bearer"));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
    String token = authService.login(loginDTO.email(), loginDTO.senha());
    return ResponseEntity.ok(new AuthResponseDTO(token, "Bearer"));
  }

}
