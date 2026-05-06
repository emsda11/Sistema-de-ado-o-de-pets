package com.example.petadocao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class PetAdocaoApplication {

    private final List<Pet> pets = new ArrayList<>();
    private int proximoId = 1;

    public static void main(String[] args) {
        SpringApplication.run(PetAdocaoApplication.class, args);
    }

    public PetAdocaoApplication() {
        pets.add(new Pet(proximoId++, "Mel", "Cachorro", 2, "Disponível"));
        pets.add(new Pet(proximoId++, "Luna", "Gato", 1, "Disponível"));
        pets.add(new Pet(proximoId++, "Thor", "Cachorro", 4, "Disponível"));
    }

    @GetMapping("/")
    public String paginaInicial() {
        return "Sistema de Adoção de Pets funcionando! Acesse /index.html";
    }

    @GetMapping("/pets")
    public List<Pet> listarPets() {
        return pets;
    }

    @GetMapping("/pets/{id}")
    public Object buscarPetPorId(@PathVariable int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                return pet;
            }
        }
        return "Pet não encontrado.";
    }

    @PostMapping("/pets")
    public String cadastrarPet(@RequestBody Pet pet) {
        if (pet.getNome() == null || pet.getNome().isBlank()) {
            return "Informe o nome do pet.";
        }

        if (pet.getTipo() == null || pet.getTipo().isBlank()) {
            return "Informe o tipo do pet.";
        }

        pet.setId(proximoId++);
        pet.setStatus("Disponível");
        pets.add(pet);

        return "Pet cadastrado com sucesso!";
    }

    @PutMapping("/pets/{id}/adotar")
    public String adotarPet(@PathVariable int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                if ("Adotado".equalsIgnoreCase(pet.getStatus())) {
                    return "Esse pet já foi adotado.";
                }

                pet.setStatus("Adotado");
                return "Pet adotado com sucesso!";
            }
        }
        return "Pet não encontrado.";
    }

    @PutMapping("/pets/{id}")
    public String editarPet(@PathVariable int id, @RequestBody Pet petAtualizado) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                pet.setNome(petAtualizado.getNome());
                pet.setTipo(petAtualizado.getTipo());
                pet.setIdade(petAtualizado.getIdade());
                return "Pet atualizado com sucesso!";
            }
        }
        return "Pet não encontrado.";
    }

    @DeleteMapping("/pets/{id}")
    public String removerPet(@PathVariable int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                pets.remove(pet);
                return "Pet removido com sucesso!";
            }
        }
        return "Pet não encontrado.";
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
