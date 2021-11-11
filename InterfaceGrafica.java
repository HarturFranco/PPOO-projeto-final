import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

/**
 * Classe InterfaceGrafica - eh a clase onde se encontra a interface grafica do jogo
 * <p>
 * Esta classe eh parte da aplicacao "Fuga da Masmorra".
 * "Fuga da Masmorral" eh um jogo de aventura muito simples, baseado em texto.
 * <p>
 * A classe "InterfaceGrafica" eh...
 *
 * @author Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */

public class InterfaceGrafica {


    private JLabel lTitulo;
    private JLabel lMapa;
    private JLabel lMarcacoes;
    private JLabel lInformacoes;
    private JLabel lDicas;
    private JTextField tComando;
    private JFrame fJanela;
    private JPanel painelInferior;
    private JPanel painelDicas;
    private JPanel painelMarcacoes;
    private JPanel painelTitulo;
    // Jogo e analisador.
    private Jogo jogo;
    private Analisador analisador;

    public InterfaceGrafica() {
        analisador = new Analisador();

        this.fJanela = new JFrame("Fuga da Masmorra.");
        this.lTitulo = new JLabel("Fuga da Masmorra.");
        this.lMapa = new JLabel();
        this.lMarcacoes = new JLabel("Marcações: ");
        this.lDicas = new JLabel("<html>Dicas:</html>");
        this.lInformacoes = new JLabel("<html>Bem-Vindo ao Jogo Fuga da Masmorra, um jogo de aventura cujo seu objetivo é matar o monstro," +
                "pegar sua chave e fugir da masmorra evitando seus diversos perigos; </html>");
        this.painelInferior = new JPanel();
        this.painelDicas = new JPanel();
        this.painelMarcacoes = new JPanel();
        this.painelTitulo = new JPanel();
        this.tComando = new JTextField();
        criaGUI();
    }

    /**
     * adiciona componentes ao container
     */
    private void addComponentesAoPane(Container pane) {

        // Titulo da pagina
        painelTitulo.add(lTitulo);
        painelTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.add(painelTitulo, BorderLayout.NORTH);

        // TODO - carregar do arquivo texto.
        // mapa


        lMapa.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        lMapa.setMinimumSize(new Dimension(735, 500));
        pane.add(lMapa, BorderLayout.CENTER);

        // Salas Marcadas
        painelMarcacoes.setLayout(new BoxLayout(painelMarcacoes, BoxLayout.Y_AXIS));
        painelMarcacoes.setPreferredSize(new Dimension(200, 500));
        painelMarcacoes.add(lMarcacoes);
        painelMarcacoes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.add(painelMarcacoes, BorderLayout.EAST);

        // Dicas

        painelDicas.setLayout(new BoxLayout(painelDicas, BoxLayout.Y_AXIS));
        painelDicas.setPreferredSize(new Dimension(200, 500));
//        lDicas.setMaximumSize(new Dimension(200, 500));
        painelDicas.add(lDicas);
        painelDicas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.add(painelDicas, BorderLayout.WEST);


//            lInformacoes.setPreferredSize(new Dimension(900, 75));

        // Painel Inferior com informacoes do jogo + input de comandos
//        painelInferior.setLayout(new GridLayout(, 1));
        painelInferior.setLayout(new BoxLayout(painelInferior, BoxLayout.Y_AXIS));
        painelInferior.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        painelInferior.setPreferredSize(new Dimension(1135, 200));

        lInformacoes.setPreferredSize(new Dimension(1135, 150));
        lInformacoes.setMaximumSize(new Dimension(1135, 150));
        lInformacoes.setVerticalAlignment(JLabel.TOP);
        lInformacoes.setVerticalTextPosition(JLabel.TOP);
        painelInferior.add(lInformacoes);

        JPanel painelInput = new JPanel();
        painelInput.setLayout(new BoxLayout(painelInput, BoxLayout.X_AXIS));
        painelInput.setPreferredSize(new Dimension(1135, 50));

        tComando.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                    System.out.println(actionEvent.getActionCommand());
                despachaComando(actionEvent.getActionCommand());
            }
        });

        painelInput.add(tComando);

        painelInferior.add(painelInput);




        //        painelInferior.add(tComando);

        pane.add(painelInferior, BorderLayout.PAGE_END);



    }

    /**
     * Cria a GUI.
     */
    private void criaGUI() {

        //Create and set up the window.

        fJanela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fJanela.setSize(new Dimension(1135, 600));
        fJanela.setResizable(true);
        //Set up the content pane.
        addComponentesAoPane(fJanela.getContentPane());

        fJanela.pack();
    }

    /**
     * Exibe a GUI.
     */
    public void exibir() {
        fJanela.setVisible(true);
        this.jogo = new Jogo(this);
    }


    private void despachaComando(String actionCommand) {
        // apaga comando do textArea
        tComando.setText("");

        Comando comando = analisador.pegarComando(actionCommand);

        try {
            if (jogo.processarComando(comando)) {
                sair();
            }
        }catch (InvalidParameterException e){
            atualizaDicas(e.getMessage());
        }



    }

    private void sair() {
        int ok = JOptionPane.showOptionDialog(fJanela,
                "Ao clicar em \"OK!\" o jogo será finalizado.","Fim de Jogo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "Ok!");
        if (ok == 0){
            fJanela.dispose();
        }
    }

    public void atualizaDicas(String sDicas) {
        lDicas.setText(String.format("<html>Dicas: %s</html>", sDicas));
    }

    public void atualizaSalasMarcadas(String sSalasMarcadas) {
        lMarcacoes.setText(String.format("<html>Salas Marcadas: %s</html>", sSalasMarcadas));
    }

    public void atualizaSaidaTexto(String sInformacao) {
        lInformacoes.setText(String.format("<html><pre>%s</pre></html>",sInformacao));
    }

    public void atualizarMapa(String sMapa) {
        this.lMapa.setText(sMapa);
    }


}
