package com.alivio.rede.rede_de_alivio_back.dao;

import com.alivio.rede.rede_de_alivio_back.factory.ConnectionFactory;
import com.alivio.rede.rede_de_alivio_back.exception.DAOException;
import com.alivio.rede.rede_de_alivio_back.model.Relatos;
import com.alivio.rede.rede_de_alivio_back.dto.RelatoComComentariosDTO;
import com.alivio.rede.rede_de_alivio_back.dto.ComentarioComAutorDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatosDAO {
    // Criar Relatos
    public void inserir(Relatos relato) throws DAOException {
        String sql = "INSERT INTO RELATOS (ID_AUTOR, TITULO, TEXTO, LIKES) VALUES (?,?,?,0)";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, relato.getIdAutor());
            stmt.setString(2, relato.getTitulo());
            stmt.setString(3, relato.getTexto());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException("Erro ao publicar relato", e);
        }
    }

    // Editar Relatos
    public void atualizar(Relatos relato) throws DAOException {
        String sql = "UPDATE RELATOS SET TITULO = ?, TEXTO = ? WHERE ID = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, relato.getTitulo());
            stmt.setString(2, relato.getTexto());
            stmt.setInt(3, relato.getId());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException("Erro ao dar like", e);
        }
    }

    // Dar like
    public void curtir(Relatos relato) throws DAOException {
        String sql = "UPDATE RELATOS SET LIKES = ? WHERE ID = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, relato.getLikes() + 1);
            stmt.setInt(2, relato.getId());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException("Erro ao curtir relato", e);
        }
    }

    private ComentarioDAO comentarioDAO = new ComentarioDAO();

    // Recuperar todos os relatos
    public List<RelatoComComentariosDTO> selectRelatos() throws DAOException {
        String sql = "SELECT * FROM RELATOS";
        List<RelatoComComentariosDTO> relatos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Relatos relato = new Relatos(
                        rs.getInt("ID"),
                        rs.getInt("ID_AUTOR"),
                        rs.getString("TITULO"),
                        rs.getString("TEXTO"),
                        rs.getInt("LIKES")
                );

                // Buscar comentários do relato
                List<ComentarioComAutorDTO> comentarios = comentarioDAO.selectComentariosByRelato(relato.getId());

                // Criar DTO com relato e comentários
                RelatoComComentariosDTO relatoDTO = new RelatoComComentariosDTO(relato, comentarios);
                relatos.add(relatoDTO);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar relatos com comentários", e);
        }

        return relatos;
    }

    // Recuperar um relato por id
    public RelatoComComentariosDTO selectRelatosById(int id) throws DAOException {
        String sql = "SELECT * FROM RELATOS WHERE ID = ?";
        RelatoComComentariosDTO relatoDTO = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Relatos relato = new Relatos(
                            rs.getInt("ID"),
                            rs.getInt("ID_AUTOR"),
                            rs.getString("TITULO"),
                            rs.getString("TEXTO"),
                            rs.getInt("LIKES")
                    );

                    // Buscar comentários do relato
                    List<ComentarioComAutorDTO> comentarios = comentarioDAO.selectComentariosByRelato(relato.getId());

                    // Criar DTO
                    relatoDTO = new RelatoComComentariosDTO(relato, comentarios);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar relato com comentários", e);
        }

        return relatoDTO;
    }


    // Recuperar um relato por id_autor
    public List<RelatoComComentariosDTO> selectRelatosByIdAutor(int idAutor) throws DAOException {
        String sql = "SELECT * FROM RELATOS WHERE ID_AUTOR = ?";
        List<RelatoComComentariosDTO> relatos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAutor);

            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Relatos relato = new Relatos(
                            rs.getInt("ID"),
                            rs.getInt("ID_AUTOR"),
                            rs.getString("TITULO"),
                            rs.getString("TEXTO"),
                            rs.getInt("LIKES")
                    );

                    // Buscar comentários do relato
                    List<ComentarioComAutorDTO> comentarios = comentarioDAO.selectComentariosByRelato(relato.getId());

                    // Criar DTO
                    RelatoComComentariosDTO relatoDTO = new RelatoComComentariosDTO(relato, comentarios);
                    relatos.add(relatoDTO);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar relatos com comentários", e);
        }

        return relatos;
    }
}
