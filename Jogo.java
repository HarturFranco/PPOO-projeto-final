import java.util.ArrayList;
import java.security.InvalidParameterException;

/**
 * Essa eh a classe principal da aplicacao "World of Zull". "World of Zuul" eh
 * um jogo de aventura muito simples, baseado em texto. Usuarios podem caminhar
 * em um cenario. E eh tudo! Ele realmente precisa ser estendido para fazer algo
 * interessante!
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o metodo
 * "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os
 * ambientes, cria o analisador e comeca o jogo. Ela tambeme avalia e executa os
 * comandos que o analisador retorna.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */

public class Jogo {
    private Analisador analisador;
    private Jogador jogador;
    private InterfaceGrafica ig;

    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo(InterfaceGrafica ig) {
        Mapa mapa = new Mapa("mapa.txt");
        jogador = new Jogador(mapa);

        analisador = new Analisador();

        jogador.setSalaAtual((mapa.getSalaInicio()));
        this.ig = ig;
        
        this.ig.atualizarMapa(mapa.getMapa());
        jogar();
    }

    /**
     * Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar() {
        imprimirBoasVindas();
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        ig.atualizaSaidaTexto("Bem-Vindo ao Jogo Fuga da Masmorra, um jogo de aventura cujo seu objetivo é matar o monstro," +
                " pegar sua chave da masmorra e fugir evitando os diversos perigos <br>");
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * 
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
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
//            System.out.println("Eu nao entendi o que voce disse...");
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
        if (!jogador.estaVivo()) {
            querSair = true;
        }
        return querSair;
    }

    /**
     *
     * Marca a sala passada por comando
     */
    private void marcarSala(Comando comando) {
        //
        if (!comando.temSegundaPalavra()) {
            throw new InvalidParameterException("Marcar qual(is) Salas?");
        }
        String segundaPalavra = comando.getSegundaPalavra();

        jogador.marcarSala(segundaPalavra);
        //System.out.println("Sala " + segundaPalavra + " marcada!");
        ig.atualizaDicas("Sala " + segundaPalavra + " marcada!");
        
        ig.atualizarMapa(jogador.getMapa());
        imprimirSalasMarcadas();
    }

    /**
     *
     * Desmarca a sala passada por comando
     */
    private void desmarcarSala(Comando comando) {
        //
        if (!comando.temSegundaPalavra()) {
            throw new InvalidParameterException("Marcar qual(is) Salas?");
        }
        String segundaPalavra = comando.getSegundaPalavra();

        jogador.desmarcarSala(segundaPalavra);
        //System.out.println("Sala " + segundaPalavra + " desmarcada!");
        ig.atualizaDicas("Sala " + segundaPalavra + " desmarcada!");
        
        ig.atualizarMapa(jogador.getMapa());
        imprimirSalasMarcadas();
    }

    /**
     *
     * Imprime todas as salas marcadas pelo jogador
     */
    private void imprimirSalasMarcadas() {
        String salasMarcadas = "";
        ArrayList<String> marcadas = jogador.getMarcadas();

        for (String marcada : marcadas) {
            salasMarcadas += marcada+"-";
        }
        // TODO - Enviar até vazia
        ig.atualizaSalasMarcadas(salasMarcadas);
    }

    /**
     *
     * executa a acao de fugir pelo jogador
     */
    private void fugir(Comando comando) {

        ig.atualizaSaidaTexto(jogador.fugir());

    }

    // Implementacoes dos comandos do usuario

    /**
     * Printe informacoes de ajuda. Aqui nos imprimimos algo bobo e enigmatico e a
     * lista de palavras de comando
     */
    private void imprimirAjuda() {

//        System.out.println("Suas palavras de comando sao:");
//        System.out.println(analisador.getComandos());
        ig.atualizaDicas("suas palavras de comando são: <br>" + analisador.getComandos());
    }

    /**
     * Tenta ir em uma direcao. Se existe uma saida entra no novo ambiente, caso
     * contrario imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            // se nao ha segunda palavra, nao sabemos pra onde ir...
            throw new InvalidParameterException("Ir pra onde?");
        }
        String direcao = comando.getSegundaPalavra();
        //System.out.println(jogador.entrar(direcao));
        ig.atualizaSaidaTexto(jogador.entrar(direcao));
        ig.atualizarMapa(jogador.getMapa());
    }

    /**
     * Tenta atirar em direção a uma sala, se houver uma conexao entre as salas,
     * atira, senão envia um erro.
     */
    private void atirar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            // se nao ha segunda palavra, nao sabemos pra onde atirar..
            throw new InvalidParameterException("Artirar no quê?");
        }
        String direcao = comando.getSegundaPalavra();
        ig.atualizaSaidaTexto(jogador.atirar(direcao));
    }

    /**
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nos queremos
     * realmente sair do jogo.
     * 
     * @return true, se este comando sai do jogo, false, caso contrario
     */
    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            throw new InvalidParameterException("Sair o que?");
        }
        return true; // sinaliza que nos queremos sair
    }
}
