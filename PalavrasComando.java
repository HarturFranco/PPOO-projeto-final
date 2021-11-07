import java.util.HashMap;

/**
 * Esta classe eh parte da aplicacao "World of Zuul".
 * "World of Zuul" eh um jogo de aventura muito simples, baseado em texto.  
 * 
 * Essa classe guarda uma enumeracao de todos os comandos conhecidos do
 * jogo. Ela eh usada no reconhecimento de comandos como eles sao digitados.
 *
 * @author  Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */

public class PalavrasComando
{
    // um vetor constante que guarda todas as palavras de comandos validas
    private HashMap<String, PalavraComando> comandosValidos;

    /**
     * Construtor - inicializa as palavras de comando.
     */
    public PalavrasComando()
    {
        comandosValidos = new HashMap<String, PalavraComando>();
        for(PalavraComando commando : PalavraComando.values()) {
            if(commando != PalavraComando.DESCONHECIDO) {
                comandosValidos.put(commando.toString(), commando);
            }
        }
    }


    /**
     * Encontra a PalavraCOmando associada a palavradocomando.
     * @param palavradocomando A palavra a ser procurada (string).
     * @return a PalavraConabdi correspondente a palavradocomando, ou DESCONHECIDO
     *         se nao for uma palavra vailida.
     */
    public PalavraComando getPalavraComando(String palavradocomando)
    {
        PalavraComando commando = comandosValidos.get(palavradocomando);
        if(commando != null) {
            return commando;
        }
        else {
            return PalavraComando.DESCONHECIDO;
        }
    }



    /**
     * Verifica se uma dada String eh uma palavra de comando valida. 
     * @return true se a string dada eh um comando valido,
     * false se nao eh.
     */
    public boolean ehComando(String umaString)
    {
        return comandosValidos.containsKey(umaString);
    }

    /**
     * Produz uma String com a lista com os comandos validos.
     * @return uma String com todos comandos separados por espaco.
     */
    public String getListaComandos(){
        // TODO - Com a interface isso aqui vai chamar a interface e printar no campo.
        // ou retornar a string pra que a classe jogo chame a interface
        String listaComandos = "";
        for(String comando : comandosValidos.keySet()){
            listaComandos += comando + " ";
        }
        return listaComandos;
    }


}
