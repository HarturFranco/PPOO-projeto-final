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
    private Mapa mapa;

    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo() {
        mapa = new Mapa("mapa.txt");
        jogador = new Jogador(mapa);

        analisador = new Analisador();

        jogador.setSalaAtual((mapa.getSalaInicio()));
    }

    /**
     * Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar() {
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nos repetidamente lemos
        // comandos e os executamos ate o jogo terminar.

        boolean terminado = false;
        while (!terminado) {
            try {
                Comando comando = analisador.pegarComando();
                terminado = processarComando(comando);
            } catch (InvalidParameterException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Obrigado por jogar. Ate mais!");
    }

    /**
     * Imprime informacoes sobre o ambiente atual.
     */
    private void imprimirInformacaoSobreAmbiente() {
        System.out.println("Voce esta " + jogador.getSalaAtual().getDescricao());

        System.out.print("Saidas: " + jogador.getSalaAtual().getSaidaString());

        System.out.println();
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo ao World of Zuul!");
        System.out.println("World of Zuul eh um novo jogo de aventura, incrivelmente chato.");
        System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
        System.out.println();

        imprimirInformacaoSobreAmbiente();
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * 
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando) {
        boolean querSair = false;

        PalavraComando palavraComando = comando.getPalavraDeComando();

        switch (palavraComando) {
        case AJUDA:
            imprimirAjuda();
            break;
        case ATIRAR:
            // TODO - Implementar Atirar (sala)
            atirar(comando);
            break;
        case DESCONHECIDO:
            System.out.println("Eu nao entendi o que voce disse...");
            break;
        case DESMARCAR:
            desmarcarSala(comando);
            break;
        case FUGIR:
            // TODO - Usar Excecao aqui?
            System.out.println("Fugir");
            fugir(comando);
            break;
        case IR:
            irParaAmbiente(comando);
            break;
        case MARCAR:
            // TODO - Usar Excecao aqui?
            marcarSala(comando);
            break;
        case SAIR:
            querSair = sair(comando);
            break;
        }
        if (!jogador.estaVivo()) {
            querSair = true;
            System.out.println("GAME OVER!");
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
        System.out.println("Sala " + segundaPalavra + " marcada!");

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
        System.out.println("Sala " + segundaPalavra + " desmarcada!");

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
            salasMarcadas += marcada;
        }

        if (!salasMarcadas.equals("")) {
            System.out.print("As Salas Marcadas: ");
            System.out.println(salasMarcadas);
        }
    }

    /**
     *
     * executa a acao de fugir pelo jogador
     */
    private void fugir(Comando comando) {
        // TODO - Usar Excecao aqui?
        // TODO - Mudar Texto.
        if (jogador.fugir()) {
            System.out.println("Você conseguiu Sair da Masmorra, parabéns!!!!");
            // TODO - Finalizar programa.
        } else {
            System.out.println("Você nao pode fugir!");
        }
    }

    // Implementacoes dos comandos do usuario

    /**
     * Printe informacoes de ajuda. Aqui nos imprimimos algo bobo e enigmatico e a
     * lista de palavras de comando
     */
    private void imprimirAjuda() {
        // TODO - possivel mudança para gui, chamar gui.
        System.out.println("Suas palavras de comando sao:");
        System.out.println(analisador.getComandos());
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
        System.out.println(jogador.entrar(direcao));
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
        jogador.atirar(direcao);
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
