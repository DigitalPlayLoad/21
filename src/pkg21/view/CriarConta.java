package pkg21.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import pkg21.model.Jogador; // Supondo que Jogador será usado para armazenar ou tem campos relevantes
import java.util.ArrayList; // Para um armazenamento em memória simples

public class CriarConta extends PanelBackground {

    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private Start frameStart; // Referência ao frame principal para navegação e possível armazenamento

    // Lista estática para simular armazenamento de usuários (simplificado)
    // Em uma aplicação real, isso seria um banco de dados ou arquivo.
    public static ArrayList<Jogador> usuariosCadastrados = new ArrayList<>();

    public CriarConta(Start frame) {
        this.frameStart = frame;
        setLayout(new GridBagLayout());

        JPanel painelRegistro = new JPanel();
        painelRegistro.setLayout(new BoxLayout(painelRegistro, BoxLayout.Y_AXIS));
        painelRegistro.setOpaque(false); // Para manter o fundo verde visível
        painelRegistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adiciona margem

        // Campos
        nomeField = new JTextField(20); // Aumentar um pouco o tamanho
        emailField = new JTextField(20);
        senhaField = new JPasswordField(20);

        // Labels com melhor formatação
        JLabel lblNome = new JLabel("Nome completo:");
        lblNome.setForeground(Color.WHITE);
        painelRegistro.add(lblNome);
        painelRegistro.add(nomeField);
        painelRegistro.add(Box.createRigidArea(new Dimension(0, 5))); // Espaçamento

        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setForeground(Color.WHITE);
        painelRegistro.add(lblEmail);
        painelRegistro.add(emailField);
        painelRegistro.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        painelRegistro.add(lblSenha);
        painelRegistro.add(senhaField);
        painelRegistro.add(Box.createRigidArea(new Dimension(0, 15))); // Mais espaço antes dos botões

        // Botões
        JButton btnEnviar = new JButton("Enviar");
        JButton btnVoltar = new JButton("Voltar");
        
        // Personalizar botões (opcional, mas melhora a estética)
        personalizarBotao(btnEnviar, new Color(70, 130, 180), Color.WHITE); // Azul aço
        personalizarBotao(btnVoltar, new Color(105, 105, 105), Color.WHITE); // Cinza escuro

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoesPanel.setOpaque(false);
        botoesPanel.add(btnEnviar);
        botoesPanel.add(btnVoltar);

        painelRegistro.add(botoesPanel);

        // Centraliza o painelRegistro
        add(painelRegistro, new GridBagConstraints());

        // Ação do botão Enviar
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText().trim();
                String email = emailField.getText().trim();
                String senha = new String(senhaField.getPassword()).trim();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(CriarConta.this,
                            "Todos os campos são obrigatórios!",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validação simples de e-mail (apenas verifica se contém "@")
                if (!email.contains("@") || !email.contains(".")) {
                    JOptionPane.showMessageDialog(CriarConta.this,
                            "Formato de e-mail inválido!",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Simulação de cadastro: Adiciona a um ArrayList estático
                // Em um cenário real, aqui ocorreria a inserção em banco de dados ou arquivo.
                // Usando o construtor de Jogador que aceita nome, sobrenome (pode ser vazio), email, senha, pontos (0 inicial)
                Jogador novoJogador = new Jogador(nome, "", email, senha, 0); // Sobrenome vazio, pontos iniciais 0
                usuariosCadastrados.add(novoJogador);
                
                System.out.println("Usuário Cadastrado: " + nome + " - " + email); // Log no console

                JOptionPane.showMessageDialog(CriarConta.this,
                        "Usuário " + nome + " cadastrado com sucesso!",
                        "Cadastro Realizado",
                        JOptionPane.INFORMATION_MESSAGE);

                // Limpar campos após o cadastro
                nomeField.setText("");
                emailField.setText("");
                senhaField.setText("");
                
                // Opcional: Voltar para a tela inicial após o cadastro
                // frameStart.mostrarTela("telaInicial"); 
            }
        });

        // Ação do botão Voltar
        btnVoltar.addActionListener(e -> frameStart.mostrarTela("telaInicial"));
    }
    
    private void personalizarBotao(JButton btn, Color bgColor, Color fgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(100, 30));
    }
}

