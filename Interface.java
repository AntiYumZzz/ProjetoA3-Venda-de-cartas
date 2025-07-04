import javax.swing.*; // Componentes gráficos Swing
import javax.swing.border.*; // Bordas para componentes Swing
import java.awt.*; // Layouts e gerenciamento de janelas
import java.awt.event.*; //Gerenciamento de butoes
import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class Interface {
    static String currentNome = "";

    //Este método verifica se um email e senha fornecidos correspondem a um usuário registrado no arquivo usuarios.txt
    private static boolean verificarLogin(String email, String senha) {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) { //Usa BufferedReader para ler o arquivo linha por linha.
            String line;
            String currentEmail = "";
            String currentSenha = "";


            while ((line = br.readLine()) != null) {
                if(line.startsWith("Nome: ")) {
                    currentNome = line.substring(6).trim();
                }else if (line.startsWith("E-mail: ")) {
                    currentEmail = line.substring(8).trim();
                } else if (line.startsWith("Senha: ")) {
                    currentSenha = line.substring(7).trim();

                    // DEBUG: Mostrar valores lidos e comparados
                    System.out.println("Email procurado: " + email);
                    System.out.println("Senha procurada: " + senha);
                    System.out.println("Current Nome: " + currentNome);
                    System.out.println("Current Email: " + currentEmail);
                    System.out.println("Current Senha: " + currentSenha);

                    // Verifica se o email e senha correspondem
                    if (currentEmail.equalsIgnoreCase(email) && currentSenha.equals(senha)) {
                        System.out.println("Login válido!"); // Confirmação adicional
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao verificar login: " + e.getMessage());
        }
        System.out.println("Login inválido ou usuário não encontrado."); // Mensagem se falhar
        return false;
    }



    //Método para colocar os nomes dos campos em cima das caixas
    private static JPanel criarCampoComLabel(String labelText, JComponent campo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(new Font("Arial", Font.PLAIN, 16));

        // Define tamanho máximo para o campo
        campo.setMaximumSize(new Dimension(300, 30));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5)); // Espaço entre label e campo
        panel.add(campo);

        return panel;
    }


    // Método para salvar os usuários em um arquivo TXT
    public static void salvarUsuarios(Proprietario user) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios.txt", true));
            bw.write("Nome: " + user.getNome());
            bw.newLine(); // O método bw.newLine() é usado para inserir uma quebra de linha em um arquivo de texto
            bw.write("CPF: " + user.getCpf());
            bw.newLine();
            bw.write("E-mail: " + user.getEmail());
            bw.newLine();
            bw.write("Senha: " + user.getSenha());
            bw.newLine();
            bw.write("Data de nascimento: " + user.getData());
            bw.newLine();
            bw.write("Telefone: " + user.getTele());
            bw.newLine();
            bw.write("-----------------------------");
            bw.newLine();

            bw.close();
        } catch (IOException e) { // O bloco catch (IOException e) é usado para capturar e tratar erros que podem ocorrer durante operações de entrada/saída
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    public static void salvarVeiculo(Veiculo veiculo) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("veiculos.txt", true));
            bw.write("Dono: " + currentNome);
            bw.newLine(); // O método bw.newLine() é usado para inserir uma quebra de linha em um arquivo de texto
            bw.write("Placa: " + veiculo.getPlaca());
            bw.newLine();
            bw.write("Modelo: " + veiculo.getModelo());
            bw.newLine();
            bw.write("Marca: " + veiculo.getMarca());
            bw.newLine();
            bw.write("Cor: " + veiculo.getCor());
            bw.newLine();
            bw.write("Ano: " + veiculo.getAno());
            bw.newLine();
            bw.write("-----------------------------");
            bw.newLine();

            bw.close();
        } catch (IOException e) { // O bloco catch (IOException e) é usado para capturar e tratar erros que podem ocorrer durante operações de entrada/saída
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Criação da janela principal do sistema
        JFrame frame = new JFrame("Sistema Detran");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o programa ao fechar a janela
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Janela inicia maximizada
        frame.getContentPane().setBackground(new Color(182, 182, 182)); // Cor de fundo cinza claro
        frame.setLayout(new GridBagLayout()); // Layout flexível para dimensionamento dos componentes

        // Painel principal que contém as páginas (cards) controladas pelo CardLayout
        JPanel cards = new JPanel(new CardLayout());

        // Criação das diferentes páginas/telas do sistema
        JPanel loginPage = createLoginPanel(cards); // Tela de login
        JPanel registerPage = createRegisterPanel(cards); // Tela de cadastro de usuário
        JPanel mainPage = createMainPanel(cards, "");
        mainPage.setName("Main"); // Tela principal após login, com nome de usuário padrão
        JPanel vehicleRegistrationPage = createVehicleRegistrationPanel(cards); // Tela de cadastro de veículos
        JPanel vehicleTransferencePage = createVehicleTransferencePanel(cards); // Tela de transferência de veículos
        JPanel vehicleConsultPage = createVehicleConsultPanel(cards);

        // Adiciona as páginas ao painel principal com identificadores únicos
        cards.add(loginPage, "Login");
        cards.add(registerPage, "Register");
        cards.add(mainPage, "Main");
        cards.add(vehicleRegistrationPage, "VehicleRegistration");
        cards.add(vehicleTransferencePage, "VehicleTransference");
        cards.add(vehicleConsultPage, "VehicleConsult");

        // Configuração para ocupar todo o espaço disponível na janela
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Preencher horizontal e verticalmente
        gbc.weightx = 1; // Peso para redimensionamento horizontal
        gbc.weighty = 1; // Peso para redimensionamento vertical

        frame.add(cards, gbc); // Adiciona o painel de páginas à janela principal
        frame.setSize(1200, 800); // Tamanho inicial antes de maximizar
        frame.setVisible(true); // Exibe a janela
    }

    // Métdo para criar a tela de login
    private static JPanel createLoginPanel(JPanel cards) {
        // Título superior do sistema
        JLabel titleLabel = new JLabel("Sistema Detran");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Label "Login"
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 46));

        // Campo para e-mail
        JTextField emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(300, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campo para senha
        JPasswordField password = new JPasswordField(20);
        password.setMaximumSize(new Dimension(300, 30));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label para mensagens de erro
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão de login
        JButton loginButton = new JButton("Entrar");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.setMaximumSize(new Dimension(200, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão de cadastro
        JButton registerButton = new JButton("<html><u>Cadastre-se</u></html>");
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setForeground(Color.BLUE);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setFont(new Font("Arial", Font.PLAIN, 12));
        registerButton.setMaximumSize(new Dimension(200, 40));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Painel principal que conterá todos os elementos
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(182, 182, 182));

        // Painel de login (formulário)
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 2),
                        "", TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 16),
                        Color.DARK_GRAY
                ),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        loginPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        loginPanel.setBackground(Color.WHITE);

        // Adiciona os componentes ao painel de login
        loginPanel.add(loginLabel);
        loginPanel.add(Box.createVerticalStrut(30));

        // Painel para campos de entrada
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldsPanel.setBackground(Color.WHITE);

        // Adiciona os campos com labels
        fieldsPanel.add(criarCampoComLabel("E-mail:", emailField));
        fieldsPanel.add(Box.createVerticalStrut(20));
        fieldsPanel.add(criarCampoComLabel("Senha:", password));

        // Centraliza o painel de campos
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(fieldsPanel);

        // Adiciona os demais componentes
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(errorLabel);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(registerButton);

        // Configuração do container principal
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(new Color(182, 182, 182));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);

        // Adiciona o título
        container.add(titleLabel, gbc);

        gbc.gridy++;
        container.add(loginPanel, gbc);

        // Configura ação do botão de login
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String senha = new String(password.getPassword()).trim();

            if (verificarLogin(email, senha)) {
                // Encontra e remove o painel Main existente
                for (Component comp : cards.getComponents()) {
                    if (comp.getName() != null && comp.getName().equals("Main")) {
                        cards.remove(comp);
                        break;
                    }
                }

                // Cria e adiciona o novo painel Main
                JPanel newMainPanel = createMainPanel(cards, currentNome);
                newMainPanel.setName("Main");
                cards.add(newMainPanel, "Main");

                // Mostra o painel Main
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, "Main");
            } else {
                errorLabel.setText("E-mail ou senha incorretos");
            }
        });

        // Configura ação do botão de cadastro
        registerButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, "Register");
        });

        return container;
    }



    // Tela principal após login bem-sucedido

    private static JPanel createMainPanel(JPanel cards, String username) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(182, 182, 182));

        // Mensagem de boas-vindas com o nome do usuário
        JLabel title = new JLabel("Bem vindo, " + username + ".");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Painel para botões organizados em grade 2x2
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setMaximumSize(new Dimension(600, 300));
        buttonPanel.setOpaque(false);

        // Texto dos botões principais do menu
        String[] labels = {
                "<html>Cadastro<br>Veículos</html>",
                "Relatório",
                "<html>Transferência<br>de Veículos</html>",
                "Consultar"
        };

        JButton[] buttons = new JButton[4];

        for (int i = 0; i < 4; i++) {
            buttons[i] = new JButton(labels[i]);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            buttons[i].setPreferredSize(new Dimension(200, 80));
            buttonPanel.add(buttons[i]);
        }

        // Botão "Cadastro Veículos" abre a tela de cadastro de veículos
        buttons[0].addActionListener(e -> {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, "VehicleRegistration");
        });

        // Botão "Transferência de Veículos" abre a tela correspondente
        buttons[2].addActionListener(e -> {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, "VehicleTransference");
        });

        buttons[3].addActionListener(e -> {
            // Atualiza a tela de consulta com os veículos mais recentes
            for (Component comp : cards.getComponents()) {
                if (comp.getName() != null && comp.getName().equals("VehicleConsult")) {
                    cards.remove(comp);
                    break;
                }
            }

            JPanel newConsultPanel = createVehicleConsultPanel(cards);
            newConsultPanel.setName("VehicleConsult");
            cards.add(newConsultPanel, "VehicleConsult");

            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, "VehicleConsult");
        });

        // TODO: Implementar ações para "Relatório"

        panel.add(title);
        panel.add(buttonPanel);

        return panel;
    }

    private static JPanel createVehicleConsultPanel(JPanel cards) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(182, 182, 182));

        // Título da tela
        JLabel title = new JLabel("Consulta de Veículos");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        // Painel principal para o conteúdo
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // Modelo de dados para a tabela
        Object[] columnNames = {"Placa", "Marca", "Modelo", "Cor", "Ano"};
        Object[][] data = {}; // Dados vazios inicialmente

        // Criar o modelo de tabela
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna todas as células não editáveis
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Preenche a tabela com os veículos do usuário atual
        try (BufferedReader br = new BufferedReader(new FileReader("veiculos.txt"))) {
            String line;
            String dono = "";
            String placa = "";
            String modelo = "";
            String marca = "";
            String cor = "";
            String ano = "";

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Dono: ")) {
                    dono = line.substring(6).trim();
                } else if (line.startsWith("Placa: ")) {
                    placa = line.substring(7).trim();
                } else if (line.startsWith("Modelo: ")) {
                    modelo = line.substring(8).trim();
                } else if (line.startsWith("Marca: ")) {
                    marca = line.substring(7).trim();
                } else if (line.startsWith("Cor: ")) {
                    cor = line.substring(5).trim();
                } else if (line.startsWith("Ano: ")) {
                    ano = line.substring(5).trim();
                } else if (line.equals("-----------------------------")) {
                    // Verifica se o veículo pertence ao usuário atual
                    if (dono.equals(currentNome)) {
                        // Adiciona uma nova linha ao modelo da tabela
                        model.addRow(new Object[]{placa, marca, modelo, cor, ano});
                    }
                    // Resetamos as variáveis para o próximo veículo
                    dono = "";
                    placa = "";
                    modelo = "";
                    marca = "";
                    cor = "";
                    ano = "";
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de veículos: " + e.getMessage());
            JOptionPane.showMessageDialog(panel, "Erro ao carregar veículos", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Adiciona a tabela a um JScrollPane para permitir rolagem
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton backButton = new JButton("Voltar para Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, "Main");
        });

        buttonPanel.add(backButton);

        // Adiciona os componentes ao painel principal
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }


    // Tela para cadastro de novo usuário
    private static JPanel createRegisterPanel(JPanel cards) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(182, 182, 182));

        JLabel title = new JLabel("Página de Cadastro");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Labels para campos do formulário
        String[] labels = {
                "Nome", "Data Nascimento",
                "CPF", "Email",
                "Senha", "Confirmar Senha"
        };

        // Campos de texto simples para entrada de dados
        // Campo do formulário, de cadastro
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setMaximumSize(new Dimension(400, 400));
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(Box.createVerticalGlue()); // Espaço acima
        container.add(formPanel);
        container.add(Box.createVerticalGlue()); // Espaço abaixo

        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza horizontalmente

        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza horizontalmente
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));


        // Campos do formulário de cadastro
        JTextField nomeField = new CaixaTexto("Digite seu nome");
        JTextField cpfField = new CaixaTexto("Digite seu CPF");
        JPasswordField senhaField = new CaixaSenha("Digite sua senha");
        JTextField emailField = new CaixaTexto("Digite seu e-mail");
        JTextField nascimentoField = new CaixaTexto("DD/MM/AAAA");
        JTextField telefoneField = new CaixaTexto("(xx) xxxxx-xxxx");

        // Adição dos campos com labels centralizados
        formPanel.add(criarCampoComLabel("Nome:", nomeField));
        formPanel.add(Box.createVerticalStrut(10)); // Esse código é usado para adicionar um espaço vertical fixo de 10 pixels entre componentes em um painel (formPanel) no Java Swing.

        formPanel.add(criarCampoComLabel("CPF:", cpfField));
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(criarCampoComLabel("Senha:", senhaField));
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(criarCampoComLabel("Email:", emailField));
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(criarCampoComLabel("Data Nascimento:", nascimentoField));
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(criarCampoComLabel("Telefone:", telefoneField));
        formPanel.add(Box.createVerticalStrut(20));

         // Botão para finalizar cadastro centralizado
        JButton finishButton = new JButton("Finalizar Cadastro");
        finishButton.setFont(new Font("Arial", Font.PLAIN, 20));
        finishButton.setMaximumSize(new Dimension(200, 40));
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(finishButton);
        formPanel.add(Box.createVerticalStrut(10));

        // Ação do botão
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cpf = cpfField.getText();
                String senha = new String(senhaField.getPassword());
                String email = emailField.getText();
                String nascimento = nascimentoField.getText();
                String telefone = telefoneField.getText();

                Proprietario novoUsuario = new Proprietario(nome, cpf, senha, email, nascimento, telefone);


                if(!novoUsuario.validarCpf(cpf)){
                    JOptionPane.showMessageDialog(panel, "CPF inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty() || email.isEmpty() || nascimento.isEmpty() || telefone.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Salva os usuários no arquivo txt
                salvarUsuarios(novoUsuario);


                JOptionPane.showMessageDialog(panel, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, "Login");
            }
        });






        // Botão para voltar à tela de login
        JButton backButton = new JButton("Voltar para Login");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 40));
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, "Login");
        });

        panel.add(title);
        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(finishButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);

        return panel;
    }

    // Tela de cadastro de veículos (exemplo com campos básicos)
    private static JPanel createVehicleRegistrationPanel(JPanel cards) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(182, 182, 182));

        // Título da tela
        JLabel title = new JLabel("Cadastro de Veículos");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        // Painel com os campos do formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        panel.add(formPanel, BorderLayout.CENTER);

        // Tamanho dos campos de texto
        Dimension fieldSize = new Dimension(240, 35);

        // Criação dos rótulos e campos
        JLabel labelMarca = new JLabel("Marca:");
        JTextField fieldMarca = new JTextField();
        fieldMarca.setPreferredSize(fieldSize);

        JLabel labelModelo = new JLabel("Modelo:");
        JTextField fieldModelo = new JTextField();
        fieldModelo.setPreferredSize(fieldSize);

        JLabel labelCor = new JLabel("Cor:");
        JTextField fieldCor = new JTextField();
        fieldCor.setPreferredSize(fieldSize);

        JLabel labelAno = new JLabel("Ano:");
        JTextField fieldAno = new JTextField();
        fieldAno.setPreferredSize(fieldSize);

        JLabel labelPlaca = new JLabel("Placa:");
        JTextField fieldPlaca = new JTextField();
        fieldPlaca.setPreferredSize(fieldSize);

        JRadioButton radioButtonAntigo = new JRadioButton("Formato Antigo");
        radioButtonAntigo.setActionCommand("antigo");
        JRadioButton radioButtonMerco = new JRadioButton("Formato Mercosul");
        radioButtonMerco.setActionCommand("mercosul");

        ButtonGroup groupPlaca = new ButtonGroup();
        groupPlaca.add(radioButtonAntigo);
        groupPlaca.add(radioButtonMerco);




        // Restringe o campo 'Ano' para aceitar apenas números
        fieldAno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 10, 4, 10); // Espaçamento pequeno entre label e campo
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Primeira linha: Marca e Modelo
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(labelMarca, gbc);
        gbc.gridy = 1;
        formPanel.add(fieldMarca, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(labelModelo, gbc);
        gbc.gridy = 1;
        formPanel.add(fieldModelo, gbc);

        // Segunda linha: Cor e Ano
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(labelCor, gbc);
        gbc.gridy = 3;
        formPanel.add(fieldCor, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(labelAno, gbc);
        gbc.gridy = 3;
        formPanel.add(fieldAno, gbc);

        // Terceira linha: Jradiobuttons e Placa (alinhada com a segunda coluna)
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(radioButtonAntigo, gbc);
        gbc.gridy = 6;
        formPanel.add(radioButtonMerco, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(labelPlaca, gbc);
        gbc.gridy = 5;
        formPanel.add(fieldPlaca, gbc);

        // Painel inferior com botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JButton saveButton = new JButton("Salvar");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 20));
        saveButton.setMaximumSize(new Dimension(200, 40));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = new JButton("Voltar");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setMaximumSize(new Dimension(200, 40));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ação do botão salvar
        saveButton.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String marca = fieldMarca.getText();
                String modelo = fieldModelo.getText();
                String cor = fieldCor.getText();
                String ano = fieldAno.getText();
                String placa = fieldPlaca.getText();

                Veiculo novoVeiculo = new Veiculo();

                novoVeiculo.setMarca(marca);
                novoVeiculo.setModelo(modelo);
                novoVeiculo.setCor(cor);
                novoVeiculo.setAno(ano);
                novoVeiculo.setPlaca(placa);

                ButtonModel modeloSelecionado = groupPlaca.getSelection();
                String comandoChamado = modeloSelecionado.getActionCommand();

                if (marca.isEmpty() || modelo.isEmpty() || cor.isEmpty() || ano.isEmpty()|| placa.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(comandoChamado.equals("antigo")){
                    if(!novoVeiculo.placa.validarPlacaAntiga(placa)){
                        JOptionPane.showMessageDialog(panel, "Formato de placa inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                }else if(comandoChamado.equals("merco")){
                    if(!novoVeiculo.placa.validarPlacaMerco(placa)){
                        JOptionPane.showMessageDialog(panel, "Formato de placa inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }


                // Salva o veículo

                salvarVeiculo(novoVeiculo);

                JOptionPane.showMessageDialog(panel, "Veículo cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, "Main");
            }
        }));
        // Ação do botão voltar
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, "Main");
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createVerticalStrut(15)); // Espaço entre botões
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }


    // Tela de transferência de veículos (exemplo simples)
    private static JPanel createVehicleTransferencePanel(JPanel cards) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(182, 182, 182));

        JLabel title = new JLabel("Transferência de Veículos");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        Dimension fieldSize = new Dimension(300, 35);
        Dimension smallFieldSize = new Dimension(150, 30);

        // ComboBox de seleção de veículos
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Veículo:"), gbc);

        gbc.gridx = 1;
        String[] veiculos = { "Veículo 1", "Veículo 2" }; // Placeholder, facilmente extensível
        JComboBox<String> veiculoComboBox = new JComboBox<>(veiculos);
        veiculoComboBox.setPreferredSize(smallFieldSize);
        formPanel.add(veiculoComboBox, gbc);


        // Campo Destinatário
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Destinatário:"), gbc);

        gbc.gridx = 1;
        JTextField destinatarioField = new JTextField();
        destinatarioField.setPreferredSize(fieldSize);
        destinatarioField.setMaximumSize(fieldSize);
        formPanel.add(destinatarioField, gbc);

        // Campo Data para Transferência
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Data P/ Transferência:"), gbc);

        gbc.gridx = 1;
        JTextField dataField = new JTextField();
        dataField.setPreferredSize(fieldSize);
        dataField.setMaximumSize(fieldSize);
        formPanel.add(dataField, gbc);

        // Painel para os botões, centralizado
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton transferButton = new JButton("Transferir");
        transferButton.setFont(new Font("Arial", Font.PLAIN, 20));
        transferButton.setPreferredSize(new Dimension(200, 40));

        JButton backButton = new JButton("Voltar para Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, "Main");
        });

        buttonPanel.add(transferButton);
        buttonPanel.add(backButton);

        panel.add(title);
        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(buttonPanel);

        return panel;
    }
}
