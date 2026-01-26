// Configurações do jogo
let jogoAtivo = false;
let cartasViradas = [];
let paresEncontrados = 0;
let tentativas = 0;
let timer;
let segundos = 0;

// Personagens para o jogo (pares)
const personagens = [
    { id: 1, nome: "Darth Vader", imagem: "img/dart.jpg" },
    { id: 2, nome: "Dobby", imagem: "img/dobby.png" },
    { id: 3, nome: "Hermione", imagem: "img/hermione.png" },
    { id: 4, nome: "Yoda", imagem: "img/ioda.jpg" },
    { id: 5, nome: "Jack Sparrow", imagem: "img/jack.png" },
    { id: 6, nome: "Mickey", imagem: "img/mickey.png" }
];

// Carregar melhor pontuação do localStorage
let melhorPontuacao = localStorage.getItem('melhorMemoria') || 0;
document.getElementById('melhor').textContent = melhorPontuacao;

// Iniciar jogo
function iniciarJogo() {
    if (jogoAtivo) return;
    
    jogoAtivo = true;
    cartasViradas = [];
    paresEncontrados = 0;
    tentativas = 0;
    segundos = 0;
    
    // Atualizar interface
    atualizarInterface();
    
    // Iniciar timer
    iniciarTimer();
    
    // Criar cartas
    criarCartas();
    
    // Desabilitar botão iniciar
    document.getElementById('iniciar-btn').disabled = true;
    document.getElementById('iniciar-btn').textContent = 'Jogo em andamento...';
    
    // Esconder mensagem
    document.getElementById('mensagem').style.display = 'none';
}

// Criar cartas
function criarCartas() {
    const tabuleiro = document.getElementById('tabuleiro');
    tabuleiro.innerHTML = '';
    
    // Duplicar personagens para formar pares
    let cartas = [...personagens, ...personagens];
    
    // Embaralhar cartas
    cartas = cartas.sort(() => Math.random() - 0.5);
    
    // Criar elementos das cartas
    cartas.forEach((personagem, index) => {
        const carta = document.createElement('div');
        carta.className = 'carta';
        carta.dataset.id = personagem.id;
        carta.dataset.index = index;
        
        carta.innerHTML = `
            <div class="carta-inner">
                <div class="carta-frente">?</div>
                <div class="carta-verso">
                    <img src="${personagem.imagem}" alt="${personagem.nome}">
                </div>
            </div>
        `;
        
        carta.addEventListener('click', () => virarCarta(index));
        tabuleiro.appendChild(carta);
    });
}

// Virar carta
function virarCarta(index) {
    if (!jogoAtivo) return;
    
    const carta = document.querySelector(`.carta[data-index="${index}"]`);
    
    // Verificar se já está virada
    if (carta.classList.contains('virada') || cartasViradas.length >= 2) {
        return;
    }
    
    // Virar carta
    carta.classList.add('virada');
    cartasViradas.push({ index, id: carta.dataset.id });
    
    // Verificar se duas cartas estão viradas
    if (cartasViradas.length === 2) {
        tentativas++;
        atualizarInterface();
        
        const [carta1, carta2] = cartasViradas;
        
        if (carta1.id === carta2.id) {
            // Par encontrado
            setTimeout(() => {
                document.querySelector(`.carta[data-index="${carta1.index}"]`).classList.add('encontrada');
                document.querySelector(`.carta[data-index="${carta2.index}"]`).classList.add('encontrada');
                
                paresEncontrados++;
                cartasViradas = [];
                atualizarInterface();
                
                // Verificar fim do jogo
                if (paresEncontrados === personagens.length) {
                    fimDoJogo();
                }
            }, 500);
        } else {
            // Não é par - virar de volta
            setTimeout(() => {
                document.querySelector(`.carta[data-index="${carta1.index}"]`).classList.remove('virada');
                document.querySelector(`.carta[data-index="${carta2.index}"]`).classList.remove('virada');
                cartasViradas = [];
            }, 1000);
        }
    }
}

// Fim do jogo
function fimDoJogo() {
    jogoAtivo = false;
    clearInterval(timer);
    
    // Calcular pontuação
    const pontuacao = Math.max(100 - tentativas - Math.floor(segundos/10), 10);
    
    // Atualizar melhor pontuação
    if (pontuacao > melhorPontuacao) {
        melhorPontuacao = pontuacao;
        localStorage.setItem('melhorMemoria', pontuacao);
        document.getElementById('melhor').textContent = pontuacao;
    }
    
    // Mostrar mensagem
    const mensagem = document.getElementById('mensagem');
    mensagem.innerHTML = `
        <h3>🎉 Parabéns! 🎉</h3>
        <p>Você completou o jogo em ${tentativas} tentativas e ${segundos} segundos!</p>
        <p>Pontuação: ${pontuacao}</p>
    `;
    mensagem.style.display = 'block';
    
    // Habilitar botão iniciar
    document.getElementById('iniciar-btn').disabled = false;
    document.getElementById('iniciar-btn').textContent = 'Jogar Novamente';
}

// Reiniciar jogo
function reiniciarJogo() {
    jogoAtivo = false;
    clearInterval(timer);
    
    // Habilitar botão iniciar
    document.getElementById('iniciar-btn').disabled = false;
    document.getElementById('iniciar-btn').textContent = 'Iniciar Jogo';
    
    // Limpar tabuleiro
    document.getElementById('tabuleiro').innerHTML = '';
    
    // Esconder mensagem
    document.getElementById('mensagem').style.display = 'none';
    
    // Resetar valores
    cartasViradas = [];
    paresEncontrados = 0;
    tentativas = 0;
    segundos = 0;
    atualizarInterface();
}

// Iniciar timer
function iniciarTimer() {
    clearInterval(timer);
    segundos = 0;
    
    timer = setInterval(() => {
        segundos++;
        atualizarInterface();
    }, 1000);
}

// Atualizar interface
function atualizarInterface() {
    document.getElementById('tentativas').textContent = tentativas;
    document.getElementById('pares').textContent = paresEncontrados;
    document.getElementById('tempo').textContent = segundos + 's';
}

// Configurar botões
document.getElementById('iniciar-btn').addEventListener('click', iniciarJogo);
document.getElementById('reiniciar-btn').addEventListener('click', reiniciarJogo);

// Instruções iniciais
document.addEventListener('DOMContentLoaded', function() {
    const mensagem = document.getElementById('mensagem');
    mensagem.innerHTML = '<p>Clique em "Iniciar Jogo" para começar!</p>';
    mensagem.style.display = 'block';
});