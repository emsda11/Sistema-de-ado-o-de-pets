
//indicando onde a classe está dentro do projeto
package com.example.petadocao;

//imports
//imports que permitem iniciar o projeto no Spring Boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//permite criar rotas da API, como: GET, POST,PUT,DELETE
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
//permite usar uma lista para gravar os dados dos pets
import java.util.ArrayList;
import java.util.List;

//anotações
//classe principal
@SpringBootApplication
// essa classe também será uma API REST
@RestController
// permite que o html consiga acessar a API
// o ( * ) significa dizer que qualquer origem pode acessar
@CrossOrigin(origins = "*")
public class PetAdocaoApplication {
    // Lista de pets, vai guardar os pets enquanto o sistema está rodando
    private final List<Pet> pets = new ArrayList<>();
    // id automático
    private int proximoId = 1;

    public static void main(String[] args) {
        // método que inicia o nosso projeto
        SpringApplication.run(PetAdocaoApplication.class, args);
    }

    // contrutor
    public PetAdocaoApplication() {
        pets.add(new Pet(proximoId++, "Jurema", "Cadela", 1, "Disponível"));
    }

    // página inicial
    // sempre que acessar http://localhost:8080/
    @GetMapping("/")
    public String paginaInicial() {
        return "Sistema funcionando";
    }

    // listar os pets
    @GetMapping("/pets")
    public List<Pet> listarPets() {
        return pets;
    }

    // buscar através do ID
    @GetMapping("/pets/{id}")
    // temos uma endpoint, que busca um pet específico
    public Object buscarPetPorld(@PathVariable int id) {
        // se encontrar o pet, retorne para mim
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                return pet;
            }
        }
        return "Pet não encontrado";

    }

    // cadastrando o pet
    @PostMapping("/pets")
    // @RequestBody vai transformar o JSON em um objeto java
    public String cadastrarPet(@RequestBody Pet pet) {
        // verificando se o nome está vazio
        if (pet.getNome() == null || pet.getNome().isBlank()) {
            return "Informe o nome do pet";
        }
        if (pet.getTipo() == null || pet.getTipo().isBlank()) {
            return "Informe o nome do pet";
        }
        // definisnso o status do animal
        pet.setId(proximoId++);
        pet.setStatus("Disponível");
        pets.add(pet);
        return "Pet cadastrado com sucesso";

    }

    // adotar pet
    // esse endpoint vai mudar o status do pet para Adotado
    @PutMapping("/pets/{id}/adotar")
    public String adotarPet(@PathVariable int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                if ("Adotado".equalsIgnoreCase(pet.getStatus())) {
                    return "Esse pet já foi adotado";
                }
                pet.setStatus("Adotado");
                return "Pet adotado com sucesso";
            }
        }
        return "Pet não encontrado";

    }

    @PutMapping("/pets/{id}")
    public String editarPet(@PathVariable int id, @RequestBody Pet petAtualizado) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                pet.setNome(petAtualizado.getNome());
                pet.setTipo(petAtualizado.getTipo());
                pet.setIdade(petAtualizado.getIdade());
                return "Pet atualizado com sucesso";
            }
        }
        return "Pet não encontrado";
    }

    @DeleteMapping("/pets/{id}")
    public String removerPet(@PathVariable int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                pets.remove(pet);
                return "Pet removido com sucesso";
            }
        }
        return "Pet não encontrado";
    }

    public static class Pet {
        private int id;
        private String nome;
        private String tipo;
        private int idade;
        private String status;

        public Pet() {

        }

        public Pet(int id, String nome, String tipo, int idade, String status) {
            this.id = id;
            this.nome = nome;
            this.tipo = tipo;
            this.idade = idade;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public int getIdade() {
            return idade;
        }

        public void setIdade(int idade) {
            this.idade = idade;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}
