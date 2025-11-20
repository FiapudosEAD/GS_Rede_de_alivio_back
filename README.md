# ☕ Rede de Alívio - Backend API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Oracle DB](https://img.shields.io/badge/Database-Oracle-red)

API RESTful oficial da plataforma **Rede de Alívio**, desenvolvida pela organização **[FiapudosEAD](https://github.com/FiapudosEAD)**. Este serviço gerencia autenticação, persistência de relatos, comentários e interações sociais.

## 🚀 Tecnologias Utilizadas

* **[Java 17](https://www.oracle.com/java/)**: Linguagem base.
* **[Spring Boot](https://spring.io/projects/spring-boot)**: Framework para desenvolvimento web.
* **JDBC**: Conexão direta com banco de dados.
* **Oracle Database**: Banco de dados relacional.
* **JWT (JJWT)**: Segurança e autenticação via tokens.
* **Maven**: Gerenciamento de dependências.
* **Docker**: Containerização da aplicação.

## 🔌 Endpoints da API

### Autenticação (`/api/auth`)
* `POST /api/auth/register`: Cadastra um novo usuário.
* `POST /api/auth/login`: Realiza login e retorna o Token JWT.
* `GET /api/auth/usuario/{id}`: Retorna dados de um usuário específico.

### Relatos (`/api/relatos`)
* `GET /api/relatos`: Lista todos os relatos (Feed).
* `GET /api/relatos/{id}`: Detalhes de um relato específico.
* `POST /api/relatos`: Cria um novo relato (Requer Token).
* `PUT /api/relatos/{id}`: Atualiza um relato.
* `PUT /api/relatos/{id}/curtir`: Adiciona um like ao relato.
* `GET /api/relatos/autor/{idAutor}`: Lista relatos de um autor específico.

### Comentários (`/api/comentarios`)
* `POST /api/comentarios`: Adiciona um comentário (Requer Token).
* `GET /api/comentarios/relato/{idRelato}`: Lista comentários de um relato.
* `PUT /api/comentarios/{id}/curtir`: Adiciona um like ao comentário.

## 🔒 Segurança e CORS

A API implementa um filtro de segurança (`SecurityFilter`) que:
1.  Intercepta requisições para validar o Token JWT (Bearer Token).
2.  Gerencia headers **CORS** para permitir conexões do frontend hospedado na Vercel.

## 📦 Instalação e Execução

### Via Maven (Local)

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/FiapudosEAD/gs_rede_de_alivio_back.git](https://github.com/FiapudosEAD/gs_rede_de_alivio_back.git)
    cd gs_rede_de_alivio_back
    ```

2.  **Configure o Banco de Dados:**
    Verifique a classe `ConnectionFactory.java` e garanta que as credenciais do Oracle estejam corretas ou configure via variáveis de ambiente.

3.  **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

### Via Docker

1.  **Construa a imagem:**
    ```bash
    docker build -t rede-alivio-back .
    ```

2.  **Execute o container:**
    ```bash
    docker run -p 8080:8080 rede-alivio-back
    ```

## ☁️ Deploy

O projeto está configurado para deploy contínuo em plataformas como **Render** ou **Heroku**, utilizando o `Dockerfile` incluso na raiz.

## 🤝 Contribuição

Contribuições são bem-vindas! Por favor, siga o padrão de código estabelecido pela **FiapudosEAD**.

---
<p align="center">Desenvolvido por <a href="https://github.com/FiapudosEAD">FiapudosEAD</a></p>