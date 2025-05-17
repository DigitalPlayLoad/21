package pkg21.model;

import java.util.ArrayList;
import java.util.List;

public class Jogador extends Pessoa {
    
    private List<Carta> mao;
    private int pontosJogoAtual; // Pontos no jogo atual, diferente dos pontos de rank

    // Construtor para uso geral e na tela de Rank
    public Jogador(String nome, String sobrenome, String email, String senha, int pontosRank) {
        super(nome, sobrenome, email, senha);
        this.mao = new ArrayList<>();
        this.pontosJogoAtual = pontosRank; // Inicializa com os pontos de rank, pode ser usado como score geral
    }

    // Construtor simplificado para o jogo (Dealer e Jogador simples)
    public Jogador(String nome) {
        super(nome, "", "", ""); // Sobrenome, email, senha podem ser vazios ou nulos para o dealer/jogador simples
        this.mao = new ArrayList<>();
        this.pontosJogoAtual = 0;
    }

    public void adicionarCarta(Carta carta) {
        this.mao.add(carta);
    }

    public List<Carta> getMao() {
        return this.mao;
    }

    public void limparMao() {
        this.mao.clear();
    }

    public int getValorMao() {
        int valor = 0;
        int numAces = 0;
        for (Carta carta : mao) {
            if (carta.isViradaParaCima()) {
                int valorCarta = carta.getValorBlackjack();
                valor += valorCarta;
                if (carta.getValor().equalsIgnoreCase("ace")) {
                    numAces++;
                }
            }
        }
        // Ajusta o valor do Ás (de 11 para 1) se a soma estourar 21
        while (valor > 21 && numAces > 0) {
            valor -= 10;
            numAces--;
        }
        return valor;
    }
    
    public int getValorMaoVisivel() {
        int valor = 0;
        int numAces = 0;
        boolean primeiraCartaDealerVirada = false;
        // Para o dealer, a primeira carta pode estar virada para baixo inicialmente.
        // No entanto, a lógica de getValorMaoVisivel deve considerar apenas as cartas viradas para cima.
        for (Carta carta : mao) {
            if (carta.isViradaParaCima()) {
                int valorCarta = carta.getValorBlackjack();
                valor += valorCarta;
                if (carta.getValor().equalsIgnoreCase("ace")) {
                    numAces++;
                }
            }
        }
        while (valor > 21 && numAces > 0) {
            valor -= 10;
            numAces--;
        }
        return valor;
    }

    public int getPontosRank() { // Renomeado de getPontos para clareza
        return pontosJogoAtual; // Ou mantenha um atributo separado para pontos de rank se necessário
    }
    
    public void setPontosRank(int pontos) {
        this.pontosJogoAtual = pontos;
    }

    @Override
    public String toString() {
        return super.getNome() + " (Mão: " + mao.size() + " cartas, Pontos: " + getValorMao() + ")";
    }
}

