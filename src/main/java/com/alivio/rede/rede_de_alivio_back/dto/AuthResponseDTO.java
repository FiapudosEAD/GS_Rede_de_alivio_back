package com.alivio.rede.rede_de_alivio_back.dto;

public class AuthResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String token;

    public AuthResponseDTO() {}

    public AuthResponseDTO(Integer id, String nome, String email, String token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.token = token;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}