package com.alivio.rede.rede_de_alivio_back.dto;

import com.alivio.rede.rede_de_alivio_back.model.Relatos;
import java.util.List;

public class RelatoComComentariosDTO {
    private Integer id;
    private Integer idAutor;
    private String titulo;
    private String texto;
    private Integer likes;
    private List<ComentarioComAutorDTO> comentarios;

    // Constructors
    public RelatoComComentariosDTO() {}

    public RelatoComComentariosDTO(Relatos relato, List<ComentarioComAutorDTO> comentarios) {
        this.id = relato.getId();
        this.idAutor = relato.getIdAutor();
        this.titulo = relato.getTitulo();
        this.texto = relato.getTexto();
        this.likes = relato.getLikes();
        this.comentarios = comentarios;
    }

    public RelatoComComentariosDTO(Integer id, Integer idAutor, String titulo, String texto, Integer likes, List<ComentarioComAutorDTO> comentarios) {
        this.id = id;
        this.idAutor = idAutor;
        this.titulo = titulo;
        this.texto = texto;
        this.likes = likes;
        this.comentarios = comentarios;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<ComentarioComAutorDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioComAutorDTO> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "RelatoComComentariosDTO{" +
                "id=" + id +
                ", idAutor=" + idAutor +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", likes=" + likes +
                ", comentarios=" + comentarios +
                '}';
    }
}
