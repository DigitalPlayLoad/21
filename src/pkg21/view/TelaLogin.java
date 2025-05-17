package pkg21.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; // Para acessar a lista de usuários
import pkg21.model.Jogador; // Para o tipo Jogador

public class TelaLogin extends PanelBackground {
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnVoltar;
    private Start frame;

    public TelaLogin(Start frame) {
        super();
        this.frame = frame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Login de Usuário");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblEmail, gbc);

        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtEmail, gbc);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblSenha, gbc);

        txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtSenha, gbc);

        btnLogin = new JButton("Login");
        personalizarBotao(btnLogin, new Color(70, 130, 180)); // Azul aço
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnLogin, gbc);

        btnVoltar = new JButton("Voltar");
        personalizarBotao(btnVoltar, new Color(220, 20, 60)); // Vermelho carmesim
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(btnVoltar, gbc);

        // Ação do botão Login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                String senha = new String(txtSenha.getPassword());

                if (email.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(TelaLogin.this, "Por favor, preencha todos os campos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Acessa a lista de usuários cadastrados (simulação)
                ArrayList<Jogador> usuarios = CriarConta.getUsuariosCadastrados();
                boolean loginSucesso = false;
                Jogador jogadorLogado = null;
                for (Jogador usuario : usuarios) {
                    // Supõe que Jogador tem getEmail() e getSenha()
                    // Se não tiver, precisará adicionar ou usar o nome como login e uma senha padrão/simulada
                    if (usuario.getEmail().equalsIgnoreCase(email) && usuario.getSenha().equals(senha)) {
                        loginSucesso = true;
                        jogadorLogado = usuario;
                        break;
                    }
                }

                if (loginSucesso) {
                    JOptionPane.showMessageDialog(TelaLogin.this, "Login realizado com sucesso! Bem-vindo, " + jogadorLogado.getNome() + "!", "Login Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    // Aqui você pode definir o jogador logado no frame principal ou iniciar o jogo
                    frame.setJogadorLogado(jogadorLogado); // Adicionar este método em Start.java
                    frame.mostrarTela("jogo"); // Ou tela principal do jogo após login
                } else {
                    JOptionPane.showMessageDialog(TelaLogin.this, "E-mail ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão Voltar
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.mostrarTela("inicial");
            }
        });
    }

    private void personalizarBotao(JButton btn, Color corFundo) {
        btn.setBackground(corFundo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(180, 40));
    }
}

