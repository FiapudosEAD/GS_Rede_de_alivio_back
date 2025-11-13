package com.alivio.rede.rede_de_alivio_back.dto;

public class ComentarioComAutorDTO {
    private Integer id;
    private Integer idAutor;
    private String nomeAutor;
    private Integer idRelato;
    private String comentario;
    private Integer likes;

    // Constructors
    public ComentarioComAutorDTO() {}

    public ComentarioComAutorDTO(Integer id, Integer idAutor, String nomeAutor, Integer idRelato, String comentario, Integer likes) {
        this.id = id;
        this.idAutor = idAutor;
        this.nomeAutor = nomeAutor;
        this.idRelato = idRelato;
        this.comentario = comentario;
        this.likes = likes;
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

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Integer getIdRelato() {
        return idRelato;
    }

    public void setIdRelato(Integer idRelato) {
        this.idRelato = idRelato;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "ComentarioComAutorDTO{" +
                "id=" + id +
                ", idAutor=" + idAutor +
                ", nomeAutor='" + nomeAutor + '\'' +
                ", idRelato=" + idRelato +
                ", comentario='" + comentario + '\'' +
                ", likes=" + likes +
                '}';
    }
}
