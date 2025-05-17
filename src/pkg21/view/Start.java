package pkg21.view;
import javax.swing.*;
import java.awt.*;
import pkg21.model.Jogador; // Importar Jogador

public class Start extends JFrame {
    CardLayout cardLayout;
    JPanel container;
    private Jogador jogadorLogado; // Para armazenar o jogador logado

    public Start() {
        super("Blackjack 21 - Zulu JDK 23"); // Título atualizado
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Aumentar um pouco a altura para melhor visualização
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        
       //todas as telas do jogo aqui
        container.add(new TelaInicial(this), "telaInicial");
        container.add(new CriarConta(this), "criarConta");
        container.add(new TelaLogin(this), "telaLogin"); // Adicionar TelaLogin
        container.add(new Jogo(this), "jogo");
        container.add(new Rank(this), "Rank");

        //mostra a tela inicial primeiro
        cardLayout.show(container, "telaInicial");
        setContentPane(container);
        setVisible(true);
    }
    
    //controla a exibição da tela
    public void mostrarTela(String nometela) {
        cardLayout.show(container, nometela);
    }

    // Método para definir o jogador logado
    public void setJogadorLogado(Jogador jogador) {
        this.jogadorLogado = jogador;
        System.out.println("Jogador logado no Start: " + (jogador != null ? jogador.getNome() : "Nenhum"));
    }

    // Método para obter o jogador logado (pode ser útil para outras telas)
    public Jogador getJogadorLogado() {
        return this.jogadorLogado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Start::new);
    }
}

