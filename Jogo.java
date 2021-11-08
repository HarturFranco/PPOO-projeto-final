/**
 *  Essa eh a classe principal da aplicacao "World of Zull".
 *  "World of Zuul" eh um jogo de aventura muito simples, baseado em texto.
 *  Usuarios podem caminhar em um cenario. E eh tudo! Ele realmente
 *  precisa ser estendido para fazer algo interessante!
 * 
 *  Para jogar esse jogo, crie uma instancia dessa classe e chame o metodo
 *  "jogar".
 * 
 *  Essa classe principal cria e inicializa todas as outras: ela cria os
 *  ambientes, cria o analisador e comeca o jogo. Ela tambeme avalia e 
 *  executa os comandos que o analisador retorna.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */

public class Jogo 
{
    private Analisador analisador;
    private Jogador jogador;
        
    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo() 
    {
        jogador = new Jogador();
        criarAmbientes();
        analisador = new Analisador();
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private void criarAmbientes()
    {
        Sala fora, anfiteatro, cantina, laboratorio, escritorio;
      
        // cria os ambientes
        fora = new Sala("do lado de fora da entrada principal de uma universidade");
        anfiteatro = new Sala("no anfiteatro");
        cantina = new Sala("na cantina do campus");
        laboratorio = new Sala("no laboratorio de computacao");
        escritorio = new Sala("na sala de administracao dos computadores");
        
        // inicializa as saidas dos ambientes
        // saidas fora
        fora.adicionarSaida("leste", anfiteatro);
        fora.adicionarSaida("sul", laboratorio);
        fora.adicionarSaida("oeste", cantina);
        // saidas anfiteatro
        anfiteatro.adicionarSaida("oeste", fora);
        // saidas cantina
        cantina.adicionarSaida("leste", fora);
        // saidas laboratorio
        laboratorio.adicionarSaida("norte", fora);
        laboratorio.adicionarSaida("leste", escritorio);
        escritorio.adicionarSaida("oeste", laboratorio);

        jogador.setSalaAtual(fora);
        //salaAtual = fora;  // o jogo comeca do lado de fora
    }

    /**
     *  Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar() 
    {            
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nos repetidamente lemos
        // comandos e os executamos ate o jogo terminar.
                
        boolean terminado = false;
        while (! terminado) {
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
        }
        System.out.println("Obrigado por jogar. Ate mais!");
    }

    /**
     * Imprime informacoes sobre o ambiente atual.
     */
    private void imprimirInformacaoSobreAmbiente(){
        System.out.println("Voce esta " + jogador.getSalaAtual().getDescricao());

        System.out.print("Saidas: " + jogador.getSalaAtual().getSaidaString());

        System.out.println();
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas()
    {
        System.out.println();
        System.out.println("Bem-vindo ao World of Zuul!");
        System.out.println("World of Zuul eh um novo jogo de aventura, incrivelmente chato.");
        System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
        System.out.println();

        imprimirInformacaoSobreAmbiente();
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando) 
    {
        boolean querSair = false;

        PalavraComando palavraComando = comando.getPalavraDeComando();

        switch (palavraComando){
            case DESCONHECIDO:
                System.out.println("Eu nao entendi o que voce disse...");
                break;
            case AJUDA:
                imprimirAjuda();
                break;
            case IR:
                irParaAmbiente(comando);
                break;
            case SAIR:
                querSair = sair(comando);
                break;
        }

        return querSair;
    }

    // Implementacoes dos comandos do usuario

    /**
     * Printe informacoes de ajuda.
     * Aqui nos imprimimos algo bobo e enigmatico e a lista de 
     * palavras de comando
     */
    private void imprimirAjuda() 
    {
        // TODO - possivel mudança para gui, chamar gui.
        System.out.println("Suas palavras de comando sao:");
        System.out.println(analisador.getComandos());
    }

    /** 
     * Tenta ir em uma direcao. Se existe uma saida entra no 
     * novo ambiente, caso contrario imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando) 
    {
        if(!comando.temSegundaPalavra()) {
            // se nao ha segunda palavra, nao sabemos pra onde ir...
            System.out.println("Ir pra onde?");
            return;
        }

        String direcao = comando.getSegundaPalavra();

        // Tenta sair do ambiente atual
        Sala proximoSala = jogador.getSalaAtual().getSaida(direcao);

        if (proximoSala == null) {
            System.out.println("Nao ha passagem!");
        }
        else {
            jogador.setSalaAtual(proximoSala);

            imprimirInformacaoSobreAmbiente();
        }
    }

    /** 
     * "Sair" foi digitado. Verifica o resto do comando pra ver
     * se nos queremos realmente sair do jogo.
     * @return true, se este comando sai do jogo, false, caso contrario
     */
    private boolean sair(Comando comando) 
    {
        if(comando.temSegundaPalavra()) {
            System.out.println("Sair o que?");
            return false;
        }
        else {
            return true;  // sinaliza que nos queremos sair
        }
    }
}
