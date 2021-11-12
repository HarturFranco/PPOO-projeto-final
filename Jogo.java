import java.util.ArrayList;
import java.security.InvalidParameterException;

/**
 * Classe Jogo - Classe que controla o percorrer do jogo faz I/O com a GUI
 *  e executa as jogadas/comandos.
 * 
 * Esta classe eh parte da aplicacao "Fuga da Masmorra". "Fuga da Masmorral" eh
 * um jogo de aventura muito simples, baseado em texto.
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o metodo
 * "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os
 * ambientes, cria o analisador e comeca o jogo. Ela também avalia e executa os
 * comandos que o analisador retorna.
 * 
 * @author Michael Kölling, David J. Barnes, Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva
 * @version 2011.07.31 (2021.11.06)
 */

public class Jogo {
    private Analisador analisador;
    private Jogador jogador;
    private InterfaceGrafica ig;

    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo() {
        analisador = new Analisador();
        jogar();
    }

    /**
     * Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar() {
        this.ig = new InterfaceGrafica(this);
        ig.exibir();

        try {
            Mapa mapa = new Mapa("mapa.txt");
            this.jogador = new Jogador(mapa, mapa.getSalaInicio());
            imprimirBoasVindas();
        } catch (Exception e){
            ig.sair("Erro", e.getMessage());
        }
    }

    /**
     * Imprime na GUI a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        ig.atualizaSaidaTexto("Bem-Vindo ao Jogo Fuga da Masmorra, um jogo de aventura cujo seu objetivo é matar o monstro," +
                " pegar sua chave da masmorra e fugir evitando os diversos perigos <br>");
        this.ig.atualizarMapa(jogador.getMapa());
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * 
     * @param comando O comando a ser processado.
     * @return boleano true se o comando finaliza o jogo (sair, fugir,
     *  ou se o jogador morrer).
     */
    public boolean processarComando(Comando comando) {
        boolean querSair = false;

        PalavraComando palavraComando = comando.getPalavraDeComando();

        switch (palavraComando) {
        case AJUDA:
            imprimirAjuda();
            break;
        case ATIRAR:
            atirar(comando);
            break;
        case DESCONHECIDO:
            ig.atualizaDicas("Eu nao entendi o que voce disse...");
            break;
        case DESMARCAR:
            desmarcarSala(comando);
            break;
        case FUGIR:
            fugir(comando);
            break;
        case IR:
            irParaAmbiente(comando);
            break;
        case MARCAR:
            marcarSala(comando);
            break;
        case SAIR:
            querSair = sair(comando);
            break;
        }
        
        //Se o jogador morrer ou fugir, o jogo acaba.
        if (!jogador.estaVivo() || jogador.estaLivre()) {
            querSair = true;
        }
        return querSair;
    }

    /**
     * Marca a sala passada por comando
     * @param comando comando passado, deve 
     *  ter uma segunda palavra como o 
     *  código da sala a ser marcada.
     */
    private void marcarSala(Comando comando) {
        //
        if (!comando.temSegundaPalavra()) {
            throw new InvalidParameterException("Marcar qual(is) Salas?");
        }
        String segundaPalavra = comando.getSegundaPalavra();

        jogador.marcarSala(segundaPalavra);
        
        //atualiza a GUI
        ig.atualizaDicas("Sala " + segundaPalavra + " marcada!");
        ig.atualizarMapa(jogador.getMapa());
        imprimirSalasMarcadas();
    }

    /**
     * Desmarca a sala passada por comando
     * @param comando comando passado, deve 
     *  ter uma segunda palavra como o 
     *  código da sala a ser desmarcada.
     */
    private void desmarcarSala(Comando comando) {
        //
        if (!comando.temSegundaPalavra()) {
            throw new InvalidParameterException("Marcar qual(is) Salas?");
        }
        String segundaPalavra = comando.getSegundaPalavra();

        jogador.desmarcarSala(segundaPalavra);
        
        ig.atualizaDicas("Sala " + segundaPalavra + " desmarcada!");
        ig.atualizarMapa(jogador.getMapa());
        imprimirSalasMarcadas();
    }

    /**
     * Imprime todas as salas marcadas pelo jogador
     */
    private void imprimirSalasMarcadas() {
        String salasMarcadas = "";
        ArrayList<String> marcadas = jogador.getMarcadas();

        for (String marcada : marcadas) {
            salasMarcadas += marcada+"-";
        }
        ig.atualizaSalasMarcadas(salasMarcadas);
    }

    /**
     * executa a acao de fugir pelo jogador
     * @param comando comando passado, deve 
     *  não ter uma segunda palavra.
     */
    private void fugir(Comando comando) {
        if (comando.temSegundaPalavra()) {
            throw new InvalidParameterException("fugir o que?");
        }
        
        String saida = jogador.fugir();

        //Se o jogador não consegue fugir, é uma mensagem de 'ajuda'
        // então é colocada em um painel diferente.
        if(jogador.estaLivre()){
            ig.atualizaSaidaTexto(saida);
        }else {
            ig.atualizaDicas(saida);
        }
    }

    /**
     * imprime informacoes de ajuda. Aqui nos imprimimos 
     *  a lista de palavras de comando na GUI.
     */
    private void imprimirAjuda() {
        ig.atualizaDicas("suas palavras de comando são: <br>" + analisador.getComandos());
    }

    /**
     * Tenta ir em uma sala. Se existe uma saida, entra no nova sala, caso
     *  contrario imprime mensagem de erro.
     *  Nesse método o texto da narrativa vai para GUI.
     * 
     * @param comando comando passado, deve 
     *  ter uma segunda palavra como o 
     *  código da sala para onde o jogador vai.
     */
    private void irParaAmbiente(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            throw new InvalidParameterException("Ir pra onde?");
        }
        
        String codSala = comando.getSegundaPalavra();
        ig.atualizaSaidaTexto(jogador.entrar(codSala));
        ig.atualizarMapa(jogador.getMapa());
    }


    /**
     * Tenta atirar em direção a uma sala, se houver uma conexao entre as salas,
     * atira, senão envia um erro.
     * 
     * @param comando comando passado, deve 
     *  ter uma segunda palavra como o 
     *  código da sala onde o jogador vai atirar.
     */
    private void atirar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            throw new InvalidParameterException("Artirar no quê?");
        }
        String codSala = comando.getSegundaPalavra();

        //Se o jogador não tem balas, é uma mensagem de 'ajuda'
        // então é colocada em um painel diferente.
        if (jogador.temArma()){
            ig.atualizaSaidaTexto(jogador.atirar(codSala));
        } else {
            ig.atualizaDicas(jogador.atirar(codSala));
        }

    }

    /**
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nos queremos
     * realmente sair do jogo.
     * @param comando comando passado, deve 
     *  não ter uma segunda palavra.
     * @return true, se este comando sai do jogo, false, caso contrario
     */
    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            throw new InvalidParameterException("Sair o que?");
        }
        return true; // sinaliza que nos queremos sair
    }
}
