
        const api = "http://localhost:8080/pets";

        function listarPets() {
            fetch(api)
                .then(resposta => resposta.json())
                .then(pets => {
                    const lista = document.getElementById("listaPets");
                    lista.innerHTML = "";

                    pets.forEach(pet => {
                        const classe = pet.status === "Adotado" ? "pet adotado" : "pet";

                        lista.innerHTML += `
                        <div class="${classe}">
                            <h3>${pet.nome}</h3>
                            <p><strong>ID:</strong> ${pet.id}</p>
                            <p><strong>Tipo:</strong> ${pet.tipo}</p>
                            <p><strong>Idade:</strong> ${pet.idade} ano(s)</p>
                            <p><strong>Status:</strong> ${pet.status}</p>

                            <button onclick="adotarPet(${pet.id})">Adotar</button>
                            <button class="btn-editar" onclick="prepararEdicao(${pet.id}, '${pet.nome}', '${pet.tipo}', ${pet.idade})">Editar</button>
                            <button class="btn-remover" onclick="removerPet(${pet.id})">Remover</button>
                        </div>
                    `;
                    });
                });
        }

        function salvarPet() {
            const id = document.getElementById("idPet").value;

            const pet = {
                nome: document.getElementById("nome").value,
                tipo: document.getElementById("tipo").value,
                idade: Number(document.getElementById("idade").value)
            };

            let metodo = "POST";
            let url = api;

            if (id) {
                metodo = "PUT";
                url = `${api}/${id}`;
            }

            fetch(url, {
                method: metodo,
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(pet)
            })
                .then(resposta => resposta.text())
                .then(mensagem => {
                    document.getElementById("mensagem").innerText = mensagem;
                    limparFormulario();
                    listarPets();
                });
        }

        function prepararEdicao(id, nome, tipo, idade) {
            document.getElementById("idPet").value = id;
            document.getElementById("nome").value = nome;
            document.getElementById("tipo").value = tipo;
            document.getElementById("idade").value = idade;
            document.getElementById("mensagem").innerText = "Editando pet ID " + id;
        }

        function adotarPet(id) {
            fetch(`${api}/${id}/adotar`, {
                method: "PUT"
            })
                .then(resposta => resposta.text())
                .then(mensagem => {
                    document.getElementById("mensagem").innerText = mensagem;
                    listarPets();
                });
        }

        function removerPet(id) {
            if (!confirm("Deseja remover este pet?")) {
                return;
            }

            fetch(`${api}/${id}`, {
                method: "DELETE"
            })
                .then(resposta => resposta.text())
                .then(mensagem => {
                    document.getElementById("mensagem").innerText = mensagem;
                    listarPets();
                });
        }

        function limparFormulario() {
            document.getElementById("idPet").value = "";
            document.getElementById("nome").value = "";
            document.getElementById("tipo").value = "";
            document.getElementById("idade").value = "";
        }

        listarPets();
   