package pkg21.model;

import java.awt.Image;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.net.URL;

public class Carta extends JLabel {
    private String valor;
    private String naipe;
    private boolean viradaParaCima;
    private ImageIcon imagemFace;
    private static ImageIcon imagemVerso;

    private static final int LARGURA_CARTA = 73; 
    private static final int ALTURA_CARTA = 100;

    public Carta(String valor, String naipe) {
        this.valor = valor;
        this.naipe = naipe;
        this.viradaParaCima = false; 
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(false);
        setPreferredSize(new Dimension(LARGURA_CARTA, ALTURA_CARTA));
        setSize(new Dimension(LARGURA_CARTA, ALTURA_CARTA));

        if (imagemVerso == null) {
            try {
                // Tenta carregar a imagem do verso de várias maneiras
                URL resourceUrl = getClass().getResource("/pkg21/view/cartas_novas/carta_virada.jpg");
                
                if (resourceUrl == null) {
                    resourceUrl = getClass().getClassLoader().getResource("pkg21/view/cartas_novas/carta_virada.jpg");
                }
                
                if (resourceUrl == null) {
                    resourceUrl = ClassLoader.getSystemResource("pkg21/view/cartas_novas/carta_virada.jpg");
                }
                
                if (resourceUrl != null) {
                    ImageIcon iconeOriginalVerso = new ImageIcon(resourceUrl);
                    if (iconeOriginalVerso.getImage() != null && iconeOriginalVerso.getIconWidth() > 0) {
                        Image imgRedimensionadaVerso = iconeOriginalVerso.getImage().getScaledInstance(LARGURA_CARTA, ALTURA_CARTA, Image.SCALE_SMOOTH);
                        imagemVerso = new ImageIcon(imgRedimensionadaVerso);
                        System.out.println("Imagem do verso carregada com sucesso: " + resourceUrl);
                    } else {
                        System.err.println("Imagem do verso (carta_virada.jpg) inválida");
                    }
                } else {
                    System.err.println("Imagem do verso (carta_virada.jpg) não encontrada");
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem do verso: " + e.getMessage());
                e.printStackTrace();
            }
        }

        String nomeArquivoImagem = valor.toLowerCase() + "_of_" + naipe.toLowerCase() + ".png";
        try {
            // Tenta carregar a imagem da face de várias maneiras
            URL resourceUrl = getClass().getResource("/pkg21/view/cartas_novas/" + nomeArquivoImagem);
            
            if (resourceUrl == null) {
                resourceUrl = getClass().getClassLoader().getResource("pkg21/view/cartas_novas/" + nomeArquivoImagem);
            }
            
            if (resourceUrl == null) {
                resourceUrl = ClassLoader.getSystemResource("pkg21/view/cartas_novas/" + nomeArquivoImagem);
            }
            
            if (resourceUrl != null) {
                ImageIcon iconeOriginalFace = new ImageIcon(resourceUrl);
                if (iconeOriginalFace.getImage() != null && iconeOriginalFace.getIconWidth() > 0) {
                    Image imgRedimensionadaFace = iconeOriginalFace.getImage().getScaledInstance(LARGURA_CARTA, ALTURA_CARTA, Image.SCALE_SMOOTH);
                    this.imagemFace = new ImageIcon(imgRedimensionadaFace);
                    System.out.println("Imagem da face carregada com sucesso: " + resourceUrl);
                } else {
                    System.err.println("Imagem da face (" + nomeArquivoImagem + ") inválida");
                }
            } else {
                System.err.println("Imagem da face (" + nomeArquivoImagem + ") não encontrada");
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem da face " + nomeArquivoImagem + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        atualizarImagem();
    }

    public void virarParaCima() {
        this.viradaParaCima = true;
        atualizarImagem();
    }

    public void virarParaBaixo() {
        this.viradaParaCima = false;
        atualizarImagem();
    }
    
    public boolean isViradaParaCima() {
        return viradaParaCima;
    }

    private void atualizarImagem() {
        if (viradaParaCima && imagemFace != null) {
            setIcon(imagemFace);
        } else if (!viradaParaCima && imagemVerso != null) {
            setIcon(imagemVerso);
        } else {
            setText(valor.substring(0,1).toUpperCase() + naipe.substring(0,1).toUpperCase());
            System.err.println("Fallback para texto: Imagem não disponível para " + valor + " de " + naipe + " ou verso.");
        }
    }

    public String getValor() {
        return valor;
    }

    public String getNaipe() {
        return naipe;
    }
    
    public int getValorBlackjack() {
        switch (valor.toLowerCase()) {
            case "ace":
                return 11;
            case "king":
            case "queen":
            case "jack":
                return 10;
            default:
                try {
                    return Integer.parseInt(valor);
                } catch (NumberFormatException e) {
                    System.err.println("Valor de carta inválido: " + valor);
                    return 0;
                }
        }
    }

    @Override
    public String toString() {
        return valor + "_of_" + naipe;
    }
}
