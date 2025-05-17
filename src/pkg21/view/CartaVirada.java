
package pkg21.view;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class CartaVirada extends JLabel {
    
    public CartaVirada() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(false);

        // Carrega a imagem como recurso
        ImageIcon iconeOriginal = new ImageIcon(getClass().getResource("/resources/carta_virada.jpg"));

        if (iconeOriginal.getImage() != null) {

            Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(40, 80, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(imagemRedimensionada));
        } else {
            System.err.println("Imagem não encontrada");
        }
    }
        
    }

