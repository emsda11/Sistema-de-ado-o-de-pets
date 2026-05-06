# Sistema de Adoção de Pets

Projeto simples em Java Spring Boot com API REST e HTML.
O projeto foi simplificado propositalmente para fins didáticos, concentrando controller, modelo e armazenamento em memória em uma única classe, com o intuito de facilitar a compreensão inicial do funcionamento de APIs REST no Spring Boot.

## Características

- Apenas 1 classe Java.
- API REST com GET, POST, PUT e DELETE.
- Interface HTML simples.
- Dados salvos em memória, sem banco de dados.

## Como executar

1. Abra o projeto em uma IDE.
2. Aguarde o Maven baixar as dependências.
3. Execute a classe:

```text
PetAdocaoApplication.java
```

4. Acesse no navegador:

```text
http://localhost:8080/index.html
```

## Endpoints

### Listar pets

```http
GET /pets
```

### Buscar pet por ID

```http
GET /pets/1
```

### Cadastrar pet

```http
POST /pets
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "Bob",
  "tipo": "Cachorro",
  "idade": 3
}
```

### Editar pet

```http
PUT /pets/1
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "Bob",
  "tipo": "Cachorro",
  "idade": 4
}
```

### Adotar pet

```http
PUT /pets/1/adotar
```

### Remover pet

```http
DELETE /pets/1
```
