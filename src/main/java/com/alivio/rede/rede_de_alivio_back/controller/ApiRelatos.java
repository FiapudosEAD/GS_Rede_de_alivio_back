package com.alivio.rede.rede_de_alivio_back.controller;

import com.alivio.rede.rede_de_alivio_back.dao.RelatosDAO;
import com.alivio.rede.rede_de_alivio_back.dto.RelatoComComentariosDTO;
import com.alivio.rede.rede_de_alivio_back.exception.DAOException;
import com.alivio.rede.rede_de_alivio_back.model.Relatos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatos")
@CrossOrigin(origins = "*")
public class ApiRelatos {
    private final RelatosDAO relatosDAO = new RelatosDAO();

    // Criar novo relato
    // Endpoint: POST /api/relatos
    // Body: {"idAutor": 1, "titulo": "Título do Relato", "texto": "Texto do Relato"}
    @PostMapping
    public ResponseEntity<Map<String, Object>> criarRelato(@RequestBody Relatos relato) {
        Map<String, Object> response = new HashMap<>();
        try {
            relatosDAO.inserir(relato);
            response.put("success", true);
            response.put("message", "Relato criado com sucesso.");
            response.put("data", relato);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao criar relato" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Atualizar relato existente
    // Endpoint: PUT /api/relatos/{id}
    // Body: { "id": 1, "titulo": "Novo Título", "texto": "Novo texto" }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizarRelato(
            @PathVariable Integer id,
            @RequestBody Relatos relato) {
        Map<String, Object> response = new HashMap<>();
        try {
            relato.setId(id);
            relatosDAO.atualizar(relato);
            response.put("success", true);
            response.put("message", "Relato atualizado com sucesso");
            response.put("data", relato);
            return ResponseEntity.ok(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao atualizar relato: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Curtir relato
    // Endpoint: PUT /api/relatos/{id}/curtir
    @PutMapping("/{id}/curtir")
    public ResponseEntity<Map<String, Object>> curtirRelato(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Primeiro busca o relato para pegar os likes atuais
            RelatoComComentariosDTO relatoDTO = relatosDAO.selectRelatosById(id);

            if (relatoDTO == null) {
                response.put("success", false);
                response.put("message", "Relato não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Cria objeto Relatos com os dados atuais
            Relatos relato = new Relatos();
            relato.setId(relatoDTO.getId());
            relato.setLikes(relatoDTO.getLikes());

            // Incrementa o like
            relatosDAO.curtir(relato);

            response.put("success", true);
            response.put("message", "Relato curtido com sucesso");
            response.put("likes", relatoDTO.getLikes() + 1);
            return ResponseEntity.ok(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao curtir relato: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar todos os relatos com comentários
    // Endpoint: GET /api/relatos
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodosRelatos() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<RelatoComComentariosDTO> relatos = relatosDAO.selectRelatos();
            response.put("success", true);
            response.put("message", "Relatos recuperados com sucesso");
            response.put("data", relatos);
            response.put("total", relatos.size());
            return ResponseEntity.ok(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao buscar relatos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar relato por ID
    // Endpoint: GET /api/relatos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarRelatoPorId(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            RelatoComComentariosDTO relato = relatosDAO.selectRelatosById(id);

            if (relato == null) {
                response.put("success", false);
                response.put("message", "Relato não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("success", true);
            response.put("message", "Relato recuperado com sucesso");
            response.put("data", relato);
            return ResponseEntity.ok(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao buscar relato: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar relatos por ID do autor
    // Endpoint: GET /api/relatos/autor/{idAutor}
    @GetMapping("/autor/{idAutor}")
    public ResponseEntity<Map<String, Object>> buscarRelatosPorAutor(@PathVariable Integer idAutor) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<RelatoComComentariosDTO> relatos = relatosDAO.selectRelatosByIdAutor(idAutor);
            response.put("success", true);
            response.put("message", "Relatos do autor recuperados com sucesso");
            response.put("data", relatos);
            response.put("total", relatos.size());
            return ResponseEntity.ok(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao buscar relatos do autor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
