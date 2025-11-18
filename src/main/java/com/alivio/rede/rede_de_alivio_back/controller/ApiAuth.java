package com.alivio.rede.rede_de_alivio_back.controller;

import com.alivio.rede.rede_de_alivio_back.dao.UsuarioDAO;
import com.alivio.rede.rede_de_alivio_back.dto.AuthResponseDTO;
import com.alivio.rede.rede_de_alivio_back.dto.LoginRequestDTO;
import com.alivio.rede.rede_de_alivio_back.dto.RegisterRequestDTO;
import com.alivio.rede.rede_de_alivio_back.exception.DAOException;
import com.alivio.rede.rede_de_alivio_back.model.Usuarios;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class ApiAuth {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Registrar novo usuario
    // Endpoint: POST /api/auth/register
    // Body: {"nome": "Nome do Usuario", "email": "email@example.com", "senha": "senha123"}
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registrar(@RequestBody RegisterRequestDTO request) {
        Map<String, Object> response = new HashMap<>();

        // Validar campos obrigatorios
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Nome e obrigatorio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Email e obrigatorio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (request.getSenha() == null || request.getSenha().length() < 6) {
            response.put("success", false);
            response.put("message", "Senha deve ter no minimo 6 caracteres");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Usuarios usuario = new Usuarios();
            usuario.setNome(request.getNome());
            usuario.setEmail(request.getEmail());
            usuario.setSenha(request.getSenha());

            usuarioDAO.registrar(usuario);

            AuthResponseDTO authResponse = new AuthResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail()
            );

            response.put("success", true);
            response.put("message", "Usuario registrado com sucesso");
            response.put("data", authResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Login de usuario
    // Endpoint: POST /api/auth/login
    // Body: {"email": "email@example.com", "senha": "senha123"}
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDTO request) {
        Map<String, Object> response = new HashMap<>();

        // Validar campos obrigatorios
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Email e obrigatorio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (request.getSenha() == null || request.getSenha().trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Senha e obrigatoria");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Usuarios usuario = usuarioDAO.login(request.getEmail(), request.getSenha());

            if (usuario == null) {
                response.put("success", false);
                response.put("message", "Email ou senha invalidos");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            AuthResponseDTO authResponse = new AuthResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail()
            );

            response.put("success", true);
            response.put("message", "Login realizado com sucesso");
            response.put("data", authResponse);
            return ResponseEntity.ok(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao realizar login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar usuario por ID
    // Endpoint: GET /api/auth/usuario/{id}
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Map<String, Object>> buscarUsuario(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Usuarios usuario = usuarioDAO.buscarPorId(id);

            if (usuario == null) {
                response.put("success", false);
                response.put("message", "Usuario nao encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            AuthResponseDTO authResponse = new AuthResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail()
            );

            response.put("success", true);
            response.put("message", "Usuario recuperado com sucesso");
            response.put("data", authResponse);
            return ResponseEntity.ok(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao buscar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
