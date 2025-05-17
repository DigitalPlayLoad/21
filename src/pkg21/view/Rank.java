package pkg21.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pkg21.model.Jogador;

public class Rank extends PanelBackground {

    public Rank(Start frame) {
        // Usa um painel com GridBagLayout para centralizar verticalmente
        setLayout(new GridBagLayout());

        ArrayList<Jogador> jogadores = new ArrayList<>();
        jogadores.add(new Jogador("Marcio", "dos Anjos", "ma@gmail", "1232", 5));
        jogadores.add(new Jogador("Marlon", "dos Anjos", "ma@gmail", "1232", 4));

        // Ordena por pontuação (decrescente)
        jogadores.sort((a, b) -> Integer.compare(b.getPontosRank(), a.getPontosRank()));

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Rank", "Nome", "Pontuação"}, 0);
        int rank = 1;
        int posicao = 1;
        int ultimaPontuacao = -1;

        for (Jogador j : jogadores) {
            if (j.getPontosRank() != ultimaPontuacao) {
                rank = posicao;
                ultimaPontuacao = j.getPontosRank();
            }
            model.addRow(new Object[]{rank, j.getNome(), j.getPontosRank()});
            posicao++;
        }

        JTable tabela = new JTable(model);
        tabela.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));    
        tabela.setForeground(Color.WHITE);
        tabela.setEnabled(false);
        tabela.setOpaque(false);
        tabela.setBackground(new java.awt.Color(0,0,0,0)); // transparente

        // Calcula altura necessária
        int alturaTotal = tabela.getRowHeight() * tabela.getRowCount()
                + tabela.getTableHeader().getPreferredSize().height;

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(400, alturaTotal));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        // Painel intermediário para layout limpo
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setOpaque(false);
        painelCentral.add(scroll, BorderLayout.CENTER);

        // Centraliza verticalmente com GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(painelCentral, gbc);
        
        JPanel painelBtnVoltar = new JPanel(new BorderLayout());
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(Color.LIGHT_GRAY);
        btnVoltar.setPreferredSize(new Dimension(200,50));
        painelBtnVoltar.add(btnVoltar);
        
        btnVoltar.addActionListener(e-> {
            frame.mostrarTela("telaInicial");
        });
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(painelBtnVoltar, gbc);
    }
}

