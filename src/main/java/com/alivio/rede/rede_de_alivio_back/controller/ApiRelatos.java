package com.alivio.rede.rede_de_alivio_back.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alivio.rede.rede_de_alivio_back.dao.RelatosDAO;
import com.alivio.rede.rede_de_alivio_back.dto.RelatoComComentariosDTO;
import com.alivio.rede.rede_de_alivio_back.exception.DAOException;
import com.alivio.rede.rede_de_alivio_back.model.Relatos;

@RestController
@RequestMapping("/api/relatos")
@CrossOrigin(origins = "*")
public class ApiRelatos {
    private final RelatosDAO relatosDAO = new RelatosDAO();

    @PostMapping
    public ResponseEntity<Map<String, Object>> criarRelato(@RequestBody Relatos relato) {
        Map<String, Object> response = new HashMap<>();
        try {
            relatosDAO.inserir(relato);
            response.put("success", true);
            response.put("message", "Relato criado com sucesso.");
            response.put("data", relato);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao criar relato" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

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

    @PutMapping("/{id}/curtir")
    public ResponseEntity<Map<String, Object>> curtirRelato(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            RelatoComComentariosDTO relatoDTO = relatosDAO.selectRelatosById(id);

            if (relatoDTO == null) {
                response.put("success", false);
                response.put("message", "Relato não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Relatos relato = new Relatos();
            relato.setId(relatoDTO.getId());
            relato.setLikes(relatoDTO.getLikes());

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