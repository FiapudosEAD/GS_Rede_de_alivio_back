package com.alivio.rede.rede_de_alivio_back.controller;

import com.alivio.rede.rede_de_alivio_back.dao.ComentarioDAO;
import com.alivio.rede.rede_de_alivio_back.dto.ComentarioComAutorDTO;
import com.alivio.rede.rede_de_alivio_back.exception.DAOException;
import com.alivio.rede.rede_de_alivio_back.model.Comentarios;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "*")
public class ApiComentarios {

    private final ComentarioDAO comentarioDAO = new ComentarioDAO();

    // Criar novo comentário
    // Endpoint: POST /api/comentarios
    // Body: { "idAutor": 1, "idRelato": 1, "comentario": "Texto do comentário" }
    @PostMapping
    public ResponseEntity<Map<String, Object>> criarComentario(@RequestBody Comentarios comentario) {
        Map<String, Object> response = new HashMap<>();
        try {
            comentarioDAO.inserir(comentario);
            response.put("success", true);
            response.put("message", "Comentário criado com sucesso");
            response.put("data", comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao criar comentário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Curtir comentário (incrementar likes)
    // Endpoint: PUT /api/comentarios/{id}/curtir
    @PutMapping("/{id}/curtir")
    public ResponseEntity<Map<String, Object>> curtirComentario(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Busca o comentário atual
            Comentarios comentario = comentarioDAO.selectComentarioById(id);

            if (comentario == null) {
                response.put("success", false);
                response.put("message", "Comentário não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Incrementa o like
            comentarioDAO.curtir(comentario);

            response.put("success", true);
            response.put("message", "Comentário curtido com sucesso");
            response.put("likes", comentario.getLikes() + 1);
            return ResponseEntity.ok(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao curtir comentário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET - Buscar comentários por ID do relato
    // Endpoint: GET /api/comentarios/relato/{idRelato}
    @GetMapping("/relato/{idRelato}")
    public ResponseEntity<Map<String, Object>> buscarComentariosPorRelato(@PathVariable Integer idRelato) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ComentarioComAutorDTO> comentarios = comentarioDAO.selectComentariosByRelato(idRelato);
            response.put("success", true);
            response.put("message", "Comentários recuperados com sucesso");
            response.put("data", comentarios);
            response.put("total", comentarios.size());
            return ResponseEntity.ok(response);
        } catch (DAOException e) {
            response.put("success", false);
            response.put("message", "Erro ao buscar comentários: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
