package pkg21.view;

import pkg21.model.Carta;
import pkg21.model.Jogador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jogo extends PanelBackground {

    private Start frame;
    private JPanel panelCartasDealer;
    private JPanel panelCartasJogador;
    private JLabel labelPontuacaoJogador;
    private JLabel labelPontuacaoDealer;
    private JLabel labelResultado;
    private JButton btnComprarCarta;
    private JButton btnParar;
    private JButton btnNovoJogo;

    private ArrayList<Carta> baralho;
    private Jogador jogador;
    private Jogador dealer;

    private static final String[] NAIPES = {"clubs", "diamonds", "hearts", "spades"};
    private static final String[] VALORES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
    private Timer timerAnimacao;
    private int proximaCartaAnimacaoIndex;
    private List<Carta> cartasParaAnimar;
    private JPanel painelDestinoAnimacao;

    public Jogo(Start frame) {
        super(); // Chama o construtor de PanelBackground
        this.frame = frame;
        this.jogador = new Jogador("Jogador");
        this.dealer = new Jogador("Dealer");

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel Superior (Dealer e pontuação)
        JPanel panelSuperior = new JPanel(new BorderLayout(10,5));
        panelSuperior.setOpaque(false);
        panelCartasDealer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelCartasDealer.setOpaque(false);
        labelPontuacaoDealer = new JLabel("Dealer: 0");
        labelPontuacaoDealer.setForeground(Color.WHITE);
        labelPontuacaoDealer.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(labelPontuacaoDealer, BorderLayout.NORTH);
        panelSuperior.add(panelCartasDealer, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // Painel Central (Resultado do Jogo)
        labelResultado = new JLabel(" ");
        labelResultado.setForeground(Color.YELLOW);
        labelResultado.setFont(new Font("Arial", Font.BOLD, 24));
        labelResultado.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelResultado, BorderLayout.CENTER);

        // Painel Inferior (Jogador, pontuação e botões)
        JPanel panelInferior = new JPanel(new BorderLayout(10,5));
        panelInferior.setOpaque(false);
        panelCartasJogador = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelCartasJogador.setOpaque(false);
        labelPontuacaoJogador = new JLabel("Você: 0");
        labelPontuacaoJogador.setForeground(Color.WHITE);
        labelPontuacaoJogador.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel infoJogadorPanel = new JPanel(new BorderLayout());
        infoJogadorPanel.setOpaque(false);
        infoJogadorPanel.add(labelPontuacaoJogador, BorderLayout.NORTH);
        infoJogadorPanel.add(panelCartasJogador, BorderLayout.CENTER);
        
        panelInferior.add(infoJogadorPanel, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setOpaque(false);
        btnComprarCarta = new JButton("Comprar Carta");
        btnParar = new JButton("Parar");
        btnNovoJogo = new JButton("Novo Jogo");

        personalizarBotao(btnComprarCarta);
        personalizarBotao(btnParar);
        personalizarBotao(btnNovoJogo);

        painelBotoes.add(btnComprarCarta);
        painelBotoes.add(btnParar);
        painelBotoes.add(btnNovoJogo);
        panelInferior.add(painelBotoes, BorderLayout.SOUTH);
        
        add(panelInferior, BorderLayout.SOUTH);

        btnComprarCarta.addActionListener(e -> comprarCartaParaJogador());
        btnParar.addActionListener(e -> jogadorParar());
        btnNovoJogo.addActionListener(e -> iniciarNovoJogo());

        iniciarNovoJogo();
    }

    private void personalizarBotao(JButton btn) {
        btn.setBackground(new Color(50, 50, 50));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(150, 40));
    }

    private void criarBaralho() {
        baralho = new ArrayList<>();
        for (String naipe : NAIPES) {
            for (String valor : VALORES) {
                baralho.add(new Carta(valor, naipe));
            }
        }
        Collections.shuffle(baralho);
    }

    private void iniciarNovoJogo() {
        criarBaralho();
        jogador.limparMao();
        dealer.limparMao();

        panelCartasJogador.removeAll();
        panelCartasDealer.removeAll();
        labelResultado.setText(" ");
        
        // Distribuir cartas iniciais com animação simulada
        cartasParaAnimar = new ArrayList<>();
        cartasParaAnimar.add(pegarCartaDoBaralho(true)); // Jogador 1
        cartasParaAnimar.add(pegarCartaDoBaralho(false)); // Dealer 1 (virada para baixo)
        cartasParaAnimar.add(pegarCartaDoBaralho(true)); // Jogador 2
        cartasParaAnimar.add(pegarCartaDoBaralho(true)); // Dealer 2 (virada para cima)
        
        proximaCartaAnimacaoIndex = 0;
        distribuirProximaCartaComAnimacao();

        btnComprarCarta.setEnabled(true);
        btnParar.setEnabled(true);
    }
    
    private void distribuirProximaCartaComAnimacao() {
        if (proximaCartaAnimacaoIndex >= cartasParaAnimar.size()) {
            atualizarPontuacoes();
            verificarBlackjackInicial();
            return;
        }

        Carta carta = cartasParaAnimar.get(proximaCartaAnimacaoIndex);
        
        if (proximaCartaAnimacaoIndex == 0) { // Primeira do jogador
            jogador.adicionarCarta(carta);
            painelDestinoAnimacao = panelCartasJogador;
        } else if (proximaCartaAnimacaoIndex == 1) { // Primeira do dealer (virada para baixo)
            dealer.adicionarCarta(carta); // Adiciona à mão, mas não vira ainda
            painelDestinoAnimacao = panelCartasDealer;
        } else if (proximaCartaAnimacaoIndex == 2) { // Segunda do jogador
            jogador.adicionarCarta(carta);
            painelDestinoAnimacao = panelCartasJogador;
        } else { // Segunda do dealer (virada para cima)
            dealer.adicionarCarta(carta);
            painelDestinoAnimacao = panelCartasDealer;
        }

        // Animação simples: adicionar carta e atualizar painel após um pequeno delay
        // Para uma animação de movimento real, seria necessário mais complexidade
        timerAnimacao = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                painelDestinoAnimacao.add(carta);
                painelDestinoAnimacao.revalidate();
                painelDestinoAnimacao.repaint();
                proximaCartaAnimacaoIndex++;
                distribuirProximaCartaComAnimacao(); // Chama recursivamente para a próxima carta
                ((Timer)ae.getSource()).stop(); // Para o timer após executar uma vez
            }
        });
        timerAnimacao.setRepeats(false);
        timerAnimacao.start();
    }

    private Carta pegarCartaDoBaralho(boolean viradaParaCima) {
        if (!baralho.isEmpty()) {
            Carta carta = baralho.remove(0);
            if (viradaParaCima) {
                carta.virarParaCima();
            } else {
                carta.virarParaBaixo();
            }
            return carta;
        } 
        return null; // Idealmente, tratar baralho vazio
    }

    private void comprarCartaParaJogador() {
        Carta novaCarta = pegarCartaDoBaralho(true);
        if (novaCarta != null) {
            jogador.adicionarCarta(novaCarta);
            // Animação simples de adicionar carta
            Timer compraTimer = new Timer(100, ae -> {
                panelCartasJogador.add(novaCarta);
                panelCartasJogador.revalidate();
                panelCartasJogador.repaint();
                atualizarPontuacoes();
                if (jogador.getValorMao() > 21) {
                    labelResultado.setText("Você estourou! Dealer vence.");
                    encerrarRodada();
                }
                 ((Timer)ae.getSource()).stop();
            });
            compraTimer.setRepeats(false);
            compraTimer.start();
        }
    }

    private void jogadorParar() {
        encerrarRodada();
        logicaDealer();
    }

    private void logicaDealer() {
        // Virar a carta oculta do dealer com animação
        Carta cartaOculta = null;
        for(Component comp : panelCartasDealer.getComponents()){
            if(comp instanceof Carta){
                Carta c = (Carta) comp;
                if(!c.isViradaParaCima()){
                    cartaOculta = c;
                    break;
                }
            }
        }
        
        final Carta finalCartaOculta = cartaOculta;
        if (finalCartaOculta != null) {
            Timer viraDealerTimer = new Timer(500, ae -> {
                finalCartaOculta.virarParaCima();
                panelCartasDealer.revalidate();
                panelCartasDealer.repaint();
                atualizarPontuacoes();
                ((Timer)ae.getSource()).stop();
                
                // Continuar lógica do dealer após virar a carta
                continuarLogicaDealer(); 
            });
            viraDealerTimer.setRepeats(false);
            viraDealerTimer.start();
        } else {
            continuarLogicaDealer(); // Caso não haja carta oculta (improvável no fluxo normal)
        }
    }
    
    private void continuarLogicaDealer(){
         // Dealer compra cartas até 17 ou mais
        Timer dealerCompraTimer = new Timer(700, null);
        ActionListener listenerDealer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (dealer.getValorMao() < 17 && baralho.size() > 0) {
                    Carta novaCartaDealer = pegarCartaDoBaralho(true);
                    if (novaCartaDealer != null) {
                        dealer.adicionarCarta(novaCartaDealer);
                        panelCartasDealer.add(novaCartaDealer);
                        panelCartasDealer.revalidate();
                        panelCartasDealer.repaint();
                        atualizarPontuacoes();
                    }
                } else {
                    ((Timer)evt.getSource()).stop(); // Para o timer do dealer
                    determinarVencedor();
                }
            }
        };
        dealerCompraTimer.addActionListener(listenerDealer);
        dealerCompraTimer.setInitialDelay(0); // Começa a primeira compra (se necessária) imediatamente após virar
        dealerCompraTimer.start();
    }

    private void verificarBlackjackInicial(){
        boolean jogadorTemBlackjack = jogador.getValorMao() == 21 && jogador.getMao().size() == 2;
        boolean dealerTemBlackjack = dealer.getValorMaoVisivel() == 21 && dealer.getMao().size() == 2; // Checa valor visível inicialmente

        if (jogadorTemBlackjack && dealerTemBlackjack) {
            revelarTodasCartasDealer();
            labelResultado.setText("Empate! Ambos têm Blackjack.");
            encerrarRodada();
        } else if (jogadorTemBlackjack) {
            revelarTodasCartasDealer();
            labelResultado.setText("Blackjack! Você venceu!");
            encerrarRodada();
        } else if (dealerTemBlackjack) {
            // Dealer só revela se tiver blackjack se a carta virada for 10 ou Ás
            Carta cartaVisivelDealer = null;
            for(Carta c : dealer.getMao()){
                if(c.isViradaParaCima()) cartaVisivelDealer = c;
            }
            if(cartaVisivelDealer != null && (cartaVisivelDealer.getValorBlackjack() == 10 || cartaVisivelDealer.getValorBlackjack() == 11)){
                 revelarTodasCartasDealer();
                 if(dealer.getValorMao() == 21){ // Confirma Blackjack do Dealer
                    labelResultado.setText("Dealer tem Blackjack! Dealer vence.");
                    encerrarRodada();
                 }
            }
        }
    }
    
    private void revelarTodasCartasDealer(){
        for (Component comp : panelCartasDealer.getComponents()) {
            if (comp instanceof Carta) {
                ((Carta) comp).virarParaCima();
            }
        }
        panelCartasDealer.revalidate();
        panelCartasDealer.repaint();
        atualizarPontuacoes();
    }

    private void determinarVencedor() {
        revelarTodasCartasDealer();
        int pontuacaoJogador = jogador.getValorMao();
        int pontuacaoDealer = dealer.getValorMao();

        if (pontuacaoJogador > 21) {
            // Já tratado em comprarCartaParaJogador, mas como fallback
            labelResultado.setText("Você estourou! Dealer vence.");
        } else if (pontuacaoDealer > 21) {
            labelResultado.setText("Dealer estourou! Você venceu!");
        } else if (pontuacaoJogador > pontuacaoDealer) {
            labelResultado.setText("Você venceu!");
        } else if (pontuacaoDealer > pontuacaoJogador) {
            labelResultado.setText("Dealer venceu!");
        } else {
            labelResultado.setText("Empate!");
        }
        encerrarRodada(); // Garante que botões sejam desabilitados
    }

    private void atualizarPontuacoes() {
        labelPontuacaoJogador.setText("Você: " + jogador.getValorMao());
        labelPontuacaoDealer.setText("Dealer: " + dealer.getValorMaoVisivel()); // Mostra apenas valor visível até o fim
    }

    private void encerrarRodada() {
        btnComprarCarta.setEnabled(false);
        btnParar.setEnabled(false);
    }
}

