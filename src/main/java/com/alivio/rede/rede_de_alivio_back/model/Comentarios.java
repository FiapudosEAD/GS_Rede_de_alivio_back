package com.alivio.rede.rede_de_alivio_back.model;

public class Comentarios {
    private Integer id;
    private Integer idAutor;
    private Integer idRelato;
    private String comentario;
    private Integer likes;

    //Getters e Setters
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

    //To string
    @Override
    public String toString() {
        return "Comentarios{" +
                "id=" + id +
                ", idAutor=" + idAutor +
                ", idRelato=" + idRelato +
                ", comentario='" + comentario + '\'' +
                ", likes=" + likes +
                '}';
    }
}
