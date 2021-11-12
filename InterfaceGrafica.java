import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

/**
 * Classe InterfaceGrafica - interfgace grafica do jogo.
 *
 * Esta classe eh parte da aplicacao "Fuga da Masmorra"."Fuga da Masmorra" eh
 * um jogo de aventura muito simples, baseado em texto.
 *
 * "Fuga da Masmorral" eh um jogo de aventura muito simples, baseado em texto.
 * A classe "InterfaceGrafica" cria a interface grafica do jogo que o usuario final tera acesso as funcionalidades
 * do jogo; Mostra Informacoes como dicas, o mapa do jogo, as salas marcadas pelo usuario, posicao
 * do jogador no mapa e a propria narrativa do jogo.
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

    // Jogo e analisador.
    private Jogo jogo;
    private Analisador analisador;

    /**
     * Construtor Da classe InterfaceGrafica. - inicializa cos componentes graficos, analizador e atribui o objeto
     * passaddo por parametro ao atributo jogo;
     *
     * @param jogo objeto da classe Jogo
     */
    public InterfaceGrafica(Jogo jogo) {
        analisador = new Analisador();
        this.jogo = jogo;
        this.fJanela = new JFrame("Fuga da Masmorra.");
        this.lTitulo = new JLabel("Fuga da Masmorra.");
        this.lMapa = new JLabel();
        this.lMarcacoes = new JLabel("Salas Marcadas:");
        this.lDicas = new JLabel("Dicas:");
        this.lInformacoes = new JLabel("<html>Bem-Vindo ao Jogo Fuga da Masmorra, um jogo de aventura cujo seu " +
                "objetivo é matar o monstro, pegar sua chave e fugir da masmorra evitando seus diversos perigos; </html>");
        this.tComando = new JTextField();
        criaGUI();
    }

    /**
     * adiciona componentes ao container
     */
    private void addComponentesAoPane(Container pane) {

        JPanel painelTitulo = new JPanel();

        // Titulo da pagina
        painelTitulo.add(lTitulo);
        painelTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.add(painelTitulo, BorderLayout.NORTH);

        // mapa
        lMapa.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        lMapa.setMinimumSize(new Dimension(735, 500));
        pane.add(lMapa, BorderLayout.CENTER);

        // Salas Marcadas
        JPanel painelMarcacoes = new JPanel();
        painelMarcacoes.setLayout(new BoxLayout(painelMarcacoes, BoxLayout.Y_AXIS));
        painelMarcacoes.setPreferredSize(new Dimension(200, 500));
        painelMarcacoes.add(lMarcacoes);
        painelMarcacoes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.add(painelMarcacoes, BorderLayout.EAST);

        // Dicas
        JPanel painelDicas = new JPanel();
        painelDicas.setLayout(new BoxLayout(painelDicas, BoxLayout.Y_AXIS));
        painelDicas.setPreferredSize(new Dimension(200, 500));
        painelDicas.add(lDicas);
        painelDicas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.add(painelDicas, BorderLayout.WEST);

        // Painel Inferior
        JPanel painelInferior = new JPanel();
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
                //Ao acionar o evento "Enter", chama o metodo descacha Comando
                despachaComando(actionEvent.getActionCommand());
            }
        });

        painelInput.add(tComando);
        painelInferior.add(painelInput);
        pane.add(painelInferior, BorderLayout.PAGE_END);
    }

    /**
     * Cria a GUI.
     */
    private void criaGUI() {

        //Cria e configura a janela.
        fJanela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fJanela.setSize(new Dimension(1135, 600));
        fJanela.setResizable(true);
        //Configura os pane.
        addComponentesAoPane(fJanela.getContentPane());

        fJanela.pack();
    }

    /**
     * Exibe a GUI.
     */
    public void exibir() {
        fJanela.setVisible(true);
    }

    /**
     * Despacha executa o comando digitado pelo usuario e trata.
     *
     * @param actionCommand string com comando textual entrado pelo usuario
     */
    private void despachaComando(String actionCommand) {
        // apaga comando do textArea
        atualizarDicas("");
        tComando.setText("");

        Comando comando = analisador.pegarComando(actionCommand);

        try {
            if (jogo.processarComando(comando)) {
                sair("Fim de Jogo", "Ao clicar em \"OK!\" o jogo será finalizado.");
            }
        } catch (InvalidParameterException e) {
            atualizarDicas(e.getMessage());
        }
    }

    /**
     * Exibe JOptionPane cujo acao eh finalizar o jogo.
     *
     * @param t Titulo da janela
     * @param m Mensagem a ser exibida
     */
    public void sair(String t, String m) {
        int ok = JOptionPane.showOptionDialog(fJanela,
                m, t,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "Ok!");
        if ((ok == 0) || (ok == -1)) {
            fJanela.dispose();
        }
    }

    /**
     * Atualiza o campo de dicas da Interface Grafica com o valor recebido por parametro
     *
     * @param sDicas Valor a ser inserido no campo de dicas da Interface Grafica
     */
    public void atualizarDicas(String sDicas) {
        this.lDicas.setText(String.format("<html>Dicas: %s</html>", sDicas));
    }

    /**
     * Atualiza o campo de Salas Marcadas da Interface Grafica com o valor recebido por parametro
     *
     * @param sSalasMarcadas Valor a ser inserido no campo de Salas Marcadas da Interface Grafica
     */
    public void atualizarSalasMarcadas(String sSalasMarcadas) {
        this.lMarcacoes.setText(String.format("<html>Salas Marcadas: %s</html>", sSalasMarcadas));
    }

    /**
     * Atualiza o campo de Informacoes da Interface Grafica com o valor recebido por parametro
     *
     * @param sInformacao Valor a ser inserido no campo de Informacoes da Interface Grafica
     */
    public void atualizarSaidaTexto(String sInformacao) {
        this.lInformacoes.setText(String.format("<html><pre>%s</pre></html>", sInformacao));
    }

    /**
     * Atualiza o campo do Mapa na Interface Grafica com o valor recebido por parametro
     *
     * @param sMapa Valor a ser inserido no campo do Mapa da Interface Grafica
     */
    public void atualizarMapa(String sMapa) {
        this.lMapa.setText(sMapa);
    }


}
