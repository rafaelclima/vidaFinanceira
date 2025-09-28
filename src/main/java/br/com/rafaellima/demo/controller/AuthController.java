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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/registro")
  @Operation(summary = "Registra um novo usuário", description = "Cria um novo usuário e retorna um token de acesso.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
      @ApiResponse(responseCode = "409", description = "Email já cadastrado")
  })
  public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegistroDTO registroDTO) {
    String token = authService.register(registroDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(token, "Bearer"));
  }

  @PostMapping("/login")
  @Operation(summary = "Autentica um usuário", description = "Autentica um usuário existente e retorna um token de acesso.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
      @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
      @ApiResponse(responseCode = "401", description = "Email ou senha inválidos")
  })
  public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
    String token = authService.login(loginDTO.email(), loginDTO.senha());
    return ResponseEntity.ok(new AuthResponseDTO(token, "Bearer"));
  }

}
