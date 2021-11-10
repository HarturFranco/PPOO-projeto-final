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
        jogo = new Jogo(this);
        analisador = new Analisador();


        this.fJanela = new JFrame("Fuga da Masmorra.");
        this.lTitulo = new JLabel("Fuga da Masmorra.");
        this.lMapa = new JLabel();
        this.lMarcacoes = new JLabel("Marcações: ");
        this.lDicas = new JLabel("Dicas:");
        this.lInformacoes = new JLabel("<html>Bem-Vindo ao Jogo Fuga da Masmorra, um jogo de aventura cujo seu objetivo é matar o monstro, <br>" +
                "pegar sua chave e fugir da masmorra evitando seus diversos perigos; </html>");
        this.painelInferior = new JPanel();
        this.painelDicas = new JPanel();
        this.painelMarcacoes = new JPanel();
        this.painelTitulo = new JPanel();
        this.tComando = new JTextField("");
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
        lMapa.setText(
                "<html> <pre>" +
                        "┌─────────────────────┬──────────┬──────────┬─────────────────────┐<br>" +
                        "│01                   │02        │03        │04                   │<br>" +
                        "│                     ║          ║          ║                     │<br>" +
                        "│                     │          │          │                     │<br>" +
                        "├────══────┬────══────┼──────────┼────┅┅────┼────══────┬────══────┤<br>" +
                        "│05        │06        │07        │08        │09        │10        │<br>" +
                        "│          ║          │          │          │          ║          │<br>" +
                        "│          │          │          │          │          │          │<br>" +
                        "├────══────┼────══────┼──────────┼────══────┼────══────┼────══────┤<br>" +
                        "│11-INÍCIO │12        │13        │14        │15        │16-SAÍDA  │<br>" +
                        "░          ║          ║          ║          │          ║          ▓<br>" +
                        "│          │          │          │          │          │          │<br>" +
                        "├────══────┼──────────┼────══────┼────══────┼────══────┼────══────┤<br>" +
                        "│17        │18        │19        │20        │21        │22        │<br>" +
                        "│          ║          ║          ║          │          ║          │<br>" +
                        "│          │          │          │          │          │          │<br>" +
                        "├────══────┴────══────┼──────────┼────══────┼────══────┴────══────┤<br>" +
                        "│23                   │24        │25        │26                   │<br>" +
                        "│                     ║          ║          ║                     │<br>" +
                        "│                     │          │          │                     │<br>" +
                        "└─────────────────────┴──────────┴──────────┴─────────────────────┘" +
                        "</pre></html>");

        lMapa.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        lMapa.setPreferredSize(new Dimension(735, 500));
        pane.add(lMapa, BorderLayout.CENTER);

        // Salas Marcadas
        painelMarcacoes.setPreferredSize(new Dimension(200, 500));
        painelMarcacoes.add(lMarcacoes);
        painelMarcacoes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.add(painelMarcacoes, BorderLayout.EAST);

        // Dicas

//        lDicas.setPreferredSize(new Dimension(200, 500));
        lDicas.setAlignmentX(SwingConstants.LEFT);
        painelDicas.setPreferredSize(new Dimension(200, 500));
//            GridBagConstraints gc = new GridBagConstraints();
//            gc.gridx = 0;
//            gc.gridy = 0;
//            gc.anchor = GridBagConstraints.NORTHWEST;
//            gc.insets = new Insets(2, 0, 0, 2);
//            painelDicas.add(lDicas, gc);
        painelDicas.add(lDicas);
//            painelDicas.add(lDicas);
        painelDicas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.add(painelDicas, BorderLayout.WEST);


        // Painel Inferior com informacoes do jogo + input de comandos
        painelInferior.setLayout(new GridLayout(0, 1));
        painelInferior.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tComando.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                    System.out.println(actionEvent.getActionCommand());
                despachaComando(actionEvent.getActionCommand());
            }
        });

//            lInformacoes.setPreferredSize(new Dimension(900, 75));
        painelInferior.add(lInformacoes);
        painelInferior.add(tComando);
        painelInferior.setPreferredSize(new Dimension(1200, 100));
        pane.add(painelInferior, BorderLayout.PAGE_END);


    }

    /**
     * Cria a GUI.
     */
    private void criaGUI() {

        //Create and set up the window.

        fJanela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fJanela.setPreferredSize(new Dimension(1135, 600));
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
    }


    private void despachaComando(String actionCommand) {
        // apaga comando do textArea
        tComando.setText("");

        Comando comando = analisador.pegarComando(actionCommand);

        try {
            if (jogo.processarComando(comando)) {
                fJanela.dispose();
            }
        }catch (InvalidParameterException e){
            atualizaSaidaTexto(e.getMessage());
        }



    }

    public void atualizaDicas(String sDicas) {
        lDicas.setText("<html>" + sDicas + "</html>");
    }

    public void atualizaSalasMarcadas(String sSalasMarcadas) {
        lMarcacoes.setText(sSalasMarcadas);
    }

    public void atualizaSaidaTexto(String sInformacao) {
        lInformacoes.setText(sInformacao);
    }

    public void atualizarMapa(String sMapa) {
        lMapa.setText(sMapa);
    }


}
