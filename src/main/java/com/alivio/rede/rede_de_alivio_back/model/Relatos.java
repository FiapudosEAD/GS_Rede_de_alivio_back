package com.alivio.rede.rede_de_alivio_back.model;

public class Relatos {
    private Integer id;
    private Integer idAutor;
    private String titulo;
    private String texto;
    private Integer likes;

    // Constructor

    public Relatos () {}

    public Relatos(Integer id, Integer idAutor, String titulo, String texto, Integer likes) {
        this.id = id;
        this.idAutor = idAutor;
        this.titulo = titulo;
        this.texto = texto;
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

    // To string
    @Override
    public String toString() {
        return "Relatos{" +
                "id=" + id +
                ", idAutor=" + idAutor +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", likes=" + likes +
                '}';
    }
}
