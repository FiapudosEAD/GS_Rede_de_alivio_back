package com.alivio.rede.rede_de_alivio_back.dao;

import com.alivio.rede.rede_de_alivio_back.factory.ConnectionFactory;
import com.alivio.rede.rede_de_alivio_back.exception.DAOException;
import com.alivio.rede.rede_de_alivio_back.model.Comentarios;
import com.alivio.rede.rede_de_alivio_back.dto.ComentarioComAutorDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {
    // Criar Comentário
    public void inserir(Comentarios comentario) throws DAOException {
        String sql = "INSERT INTO COMENTARIOS (ID, ID_AUTOR, ID_RELATO, COMENTARIO, LIKES) VALUES (?,?,?,?,0)";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, comentario.getId());
            stmt.setInt(2, comentario.getIdAutor());
            stmt.setInt(3, comentario.getIdRelato());
            stmt.setString(4, comentario.getComentario());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao publicar comentário", e);
        }
    }

    // Dar like
    public void curtir(Comentarios comentario) throws DAOException {
        String sql = "UPDATE COMENTARIOS SET LIKES = ? WHERE ID = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, comentario.getLikes() + 1);
            stmt.setInt(2, comentario.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao curtir comentário", e);
        }
    }

    // Retornar todos os comentários de um relato
    public List<ComentarioComAutorDTO> selectComentariosByRelato (int idRelato) throws DAOException {
        String sql = """
                SELECT c.ID, c.ID_AUTOR, c.ID_RELATO, c.COMENTARIO, c.LIKES, u.NOME
                FROM COMENTARIOS c
                INNER JOIN USUARIOS u ON c.ID_AUTOR = u.ID
                WHERE c.ID_RELATO = ?
                ORDER BY c.ID
                """;

        List<ComentarioComAutorDTO> comentarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRelato);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ComentarioComAutorDTO comentario = new ComentarioComAutorDTO(
                        rs.getInt("ID"),
                        rs.getInt("ID_AUTOR"),
                        rs.getString("NOME"),
                        rs.getInt("ID_RELATO"),
                        rs.getString("COMENTARIO"),
                        rs.getInt("LIKES")
                    );
                    comentarios.add(comentario);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar comentários do relato", e);
        }

        return comentarios;
    }
}