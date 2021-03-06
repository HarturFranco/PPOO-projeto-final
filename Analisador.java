import java.util.Scanner;

/**
 * Esta classe eh parte da aplicacao "Fuga da Masmorra". "Fuga da Masmorral" eh
 * um jogo de aventura muito simples, baseado em texto.
 *
 * Esse analisador le a entrada do usuario e tenta interpreta-la como um
 * comando "Adventure". Cada vez que eh chamado ele le uma linha do terminal
 * e tenta interpretar a linha como um comando de duas palavras. Ele retorna
 * o comando como um objeto da classe Comando.
 *
 * O analisador tem um conjunto de palavras de comando conhecidas. Ele compara
 * a entrada do usuario com os comandos conhecidos, e se a entrada nao eh um
 * dos comandos conhecidos, ele retorna um objeto comando que eh marcado como
 * um comando desconhecido.
 *
 * @author Michael Kölling and David J. Barnes, Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2011.07.31 (2016.02.01)
 */
public class Analisador {
    private PalavrasComando palavrasDeComando;  // guarda todas as palavras de comando validas

    /**
     * Cria um analisador para ler do terminal.
     */
    public Analisador() {
        palavrasDeComando = new PalavrasComando();
    }

    /**
     * Transforma string em um comando
     *
     * @param linha eh a entrada em texto do usuario
     * @return O proximo comando do usuario
     */
    public Comando pegarComando(String linha) {

        String palavra1 = null;
        String palavra2 = null;

        // Tenta encontrar ate duas palavras na linha
        Scanner tokenizer = new Scanner(linha);
        if (tokenizer.hasNext()) {
            palavra1 = tokenizer.next();      // pega a primeira palavra
            if (tokenizer.hasNext()) {
                palavra2 = tokenizer.next();      // pega a segunda palavra
                // obs: nos simplesmente ignoramos o resto da linha.
            }
        }


        // cria um comando, se a palavra1, DESCONHECIDO se desconhecido
        return new Comando(palavrasDeComando.getPalavraComando(palavra1), palavra2);
    }

    /**
     * Produz uma string com a lista de todos os comandos validos
     *
     * @return uma String com todos comandos separados por espaco.
     */
    public String getComandos() {
        return palavrasDeComando.getListaComandos();
    }
}
