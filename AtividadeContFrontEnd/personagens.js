// Dados do personagem a ser adicionado dinamicamente
const personagemDinamico = {
    nome: "Rapunzel",
    universo: "Disney (Enrolados)",
    imagem: "img/rapunzel.png",
    descricao: "Princesa sonhadora e artística que busca sua liberdade. Seu cabelo mágico de 21 metros e sua personalidade otimista a tornam uma personagem inspiradora. Sua jornada de autodescoberta mostra a importância de seguir os próprios sonhos."
};

// Função para criar o card do personagem dinâmico
function criarCardPersonagem(personagem) {
    const card = document.createElement('div');
    card.className = 'personagem-card';
    
    card.innerHTML = `
        <div class="personagem-img-container">
            <img src="${personagem.imagem}" alt="${personagem.nome}" class="personagem-img">
        </div>
        <div class="personagem-info">
            <h3 class="personagem-nome">${personagem.nome}</h3>
            <p class="personagem-universo">${personagem.universo}</p>
            <p class="personagem-descricao">${personagem.descricao}</p>
        </div>
    `;
    
    return card;
}

// Função para atualizar o contador
function atualizarContador() {
    const totalElement = document.getElementById('total-personagens');
    // Personagens estáticos (8) + 1 dinâmico = 9
    totalElement.textContent = '9';
}

// Função principal
function adicionarPersonagemDinamico() {
    const container = document.getElementById('personagem-dinamico');
    
    // Verificar se o container existe
    if (!container) {
        console.error('Container do personagem dinâmico não encontrado!');
        return;
    }
    
    // Limpar o container (caso já tenha algo)
    container.innerHTML = '';
    
    // Criar e adicionar o card do personagem
    const card = criarCardPersonagem(personagemDinamico);
    container.appendChild(card);
    
    // Atualizar o contador
    atualizarContador();
    
    // Adicionar mensagem de confirmação
    const mensagem = document.createElement('p');
    mensagem.style.marginTop = '20px';
    mensagem.style.fontStyle = 'italic';
    mensagem.style.color = '#a05c5f';
    mensagem.style.fontWeight = 'bold';
    mensagem.style.padding = '10px';
    mensagem.style.backgroundColor = 'white';
    mensagem.style.borderRadius = '5px';
    mensagem.style.border = '1px dashed #a05c5f';
    mensagem.textContent = 'Este personagem foi adicionado dinamicamente usando JavaScript!';
    container.appendChild(mensagem);
}

// Executar quando a página carregar
document.addEventListener('DOMContentLoaded', adicionarPersonagemDinamico);