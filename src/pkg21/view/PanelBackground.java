package pkg21.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color; // Importar Color para fallback
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.net.URL;

public class PanelBackground extends JPanel {
    private Image imagemFundo;

    public PanelBackground() {
        // Carrega a imagem
        try {
            // Tenta carregar a imagem de várias maneiras para garantir compatibilidade
            URL resourceUrl = getClass().getResource("/resources/mesa.jpg");
            
            if (resourceUrl == null) {
                // Tenta caminhos alternativos
                resourceUrl = getClass().getClassLoader().getResource("resources/mesa.jpg");
            }
            
            if (resourceUrl == null) {
                // Tenta caminho relativo ao pacote
                resourceUrl = getClass().getResource("/pkg21/resources/mesa.jpg");
            }
            
            if (resourceUrl == null) {
                // Último recurso - caminho absoluto dentro do classpath
                resourceUrl = ClassLoader.getSystemResource("resources/mesa.jpg");
            }
            
            if (resourceUrl != null) {
                ImageIcon icon = new ImageIcon(resourceUrl);
                // Verifica se o ImageIcon conseguiu carregar a imagem e se a imagem interna não é nula ou inválida
                if (icon != null && icon.getImage() != null && icon.getImage().getWidth(null) != -1) {
                    imagemFundo = icon.getImage();
                    System.out.println("Imagem de fundo carregada com sucesso: " + resourceUrl);
                } else {
                    System.err.println("Erro ao carregar a imagem de fundo: imagem inválida");
                    imagemFundo = null;
                }
            } else {
                System.err.println("Erro ao carregar a imagem de fundo: recurso não encontrado");
                imagemFundo = null;
            }
        } catch (Exception e) {
            System.err.println("Exceção ao carregar a imagem de fundo: " + e.getMessage());
            e.printStackTrace();
            imagemFundo = null;
        }
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemFundo != null) {
            g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(0, 100, 0)); // Verde escuro como fallback
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
