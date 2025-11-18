package com.alivio.rede.rede_de_alivio_back.dao;

import com.alivio.rede.rede_de_alivio_back.factory.ConnectionFactory;
import com.alivio.rede.rede_de_alivio_back.exception.DAOException;
import com.alivio.rede.rede_de_alivio_back.model.Usuarios;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UsuarioDAO {

    // Hash de senha usando SHA-256
    private String hashSenha(String senha) throws DAOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException("Erro ao processar senha", e);
        }
    }

    // Registrar novo usuario
    public void registrar(Usuarios usuario) throws DAOException {
        // Verificar se email ja existe
        if (buscarPorEmail(usuario.getEmail()) != null) {
            throw new DAOException("Email ja cadastrado", null);
        }

        String sql = "INSERT INTO USUARIOS (NOME, EMAIL, SENHA) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID"})) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, hashSenha(usuario.getSenha()));

            stmt.executeUpdate();

            // Recuperar ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao registrar usuario", e);
        }
    }

    // Login: validar credenciais
    public Usuarios login(String email, String senha) throws DAOException {
        String sql = "SELECT ID, NOME, EMAIL FROM USUARIOS WHERE EMAIL = ? AND SENHA = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, hashSenha(senha));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuarios usuario = new Usuarios();
                    usuario.setId(rs.getInt("ID"));
                    usuario.setNome(rs.getString("NOME"));
                    usuario.setEmail(rs.getString("EMAIL"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao realizar login", e);
        }

        return null;
    }

    // Buscar usuario por email
    public Usuarios buscarPorEmail(String email) throws DAOException {
        String sql = "SELECT ID, NOME, EMAIL FROM USUARIOS WHERE EMAIL = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuarios usuario = new Usuarios();
                    usuario.setId(rs.getInt("ID"));
                    usuario.setNome(rs.getString("NOME"));
                    usuario.setEmail(rs.getString("EMAIL"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar usuario", e);
        }

        return null;
    }

    // Buscar usuario por ID
    public Usuarios buscarPorId(Integer id) throws DAOException {
        String sql = "SELECT ID, NOME, EMAIL FROM USUARIOS WHERE ID = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuarios usuario = new Usuarios();
                    usuario.setId(rs.getInt("ID"));
                    usuario.setNome(rs.getString("NOME"));
                    usuario.setEmail(rs.getString("EMAIL"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar usuario", e);
        }

        return null;
    }
}
