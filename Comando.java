/**
 * Esta classe eh parte da aplicacao "Fuga da Masmorra". "Fuga da Masmorral" eh
 * um jogo de aventura muito simples, baseado em texto.
 *
 * Essa classe guarda informacoes sobre um comando que foi digitado pelo
 * usuario. Um comando atualmente consiste em duas strings: uma palavra
 * de comando e uma segunda palavra (por exemplo, se o campo for "pegar
 * mapa", entao as duas strings obviamente serao "pegar" e "mapa").
 *
 * Isso eh usado assim: comandos ja estao validados como comandos validos
 * Se o usuario entrou um comando invalido (uma palavra que nao eh
 * conhecida) entao o a palavra de comando eh <null>.
 *
 * Se o comando tem so uma palavra, a segunda palavra eh <null>
 *
 * @author Michael Kölling and David J. Barnes, Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva
 * @version 2011.07.31 (2016.02.01)
 */

public class Comando {
    private PalavraComando palavraDeComando;
    private String segundaPalavra;

    /**
     * Cria um objeto comando. Primeira e segunda palavra devem ser
     * fornecidas, a segunda pode ser null.
     *
     * @param palavraDeComando A palavra do comando. DESCONHECIDO se
     *                         o comando nao foi reconhecido
     * @param segundaPalavra   A segunda palavra do comando, pode ser null.
     */
    public Comando(PalavraComando palavraDeComando, String segundaPalavra) {
        this.palavraDeComando = palavraDeComando;
        this.segundaPalavra = segundaPalavra;
    }

    /**
     * Retorna a palavra de comando (a primeira palavra) deste comando.
     * Se o comando nao foi entendido, o resultado eh null.
     *
     * @return A palavra de comando.
     */
    public PalavraComando getPalavraDeComando() {
        return palavraDeComando;
    }

    /**
     * @return A segunda palavra deste comando. Retorna null se
     * nao existe segunda palavra.
     */
    public String getSegundaPalavra() {
        return segundaPalavra;
    }

    /**
     * @return true se o comando nao foi entendido.
     */
    public boolean ehDesconhecido() {
        return (palavraDeComando == PalavraComando.DESCONHECIDO);
    }

    /**
     * @return true se o comando tem uma segunda palavra.
     */
    public boolean temSegundaPalavra() {
        return (segundaPalavra != null);
    }
}

