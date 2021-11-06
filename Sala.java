import java.util.HashMap;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 *
 * Esta classe eh parte da aplicacao "World of Zuul".
 * "World of Zuul" eh um jogo de aventura muito simples, baseado em texto.  
 *
 * Um "Ambiente" representa uma localizacao no cenario do jogo. Ele eh
 * conectado aos outros ambientes atraves de saidas. As saidas sao
 * nomeadas como norte, sul, leste e oeste. Para cada direcao, o ambiente
 * guarda uma referencia para o ambiente vizinho, ou null se nao ha
 * saida naquela direcao.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */
public class Sala
{
    private String descricao;
    HashMap<String, Sala> saidas;

    /**
     * Cria um ambiente com a "descricao" passada. Inicialmente, ele
     * nao tem saidas. "descricao" eh algo como "uma cozinha" ou
     * "
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "um jardim aberto".
     * @param descricao A descricao do ambiente.
     */
    public Sala(String descricao)
    {
        this.descricao = descricao;
        saidas = new HashMap<>();
    }

    /**
     * Define uma saida desse ambiente
     * @param direcao A direcao da saida.
     * @param vizinho O ambiente na direcao dada
     */
    public void ajustarSaida(String direcao, Sala vizinho)
    {
        saidas.put(direcao, vizinho);
    }

    /**
     * @return A descricao do ambiente.
     */
    public String getDescricao()
    {
        return descricao;
    }


    /**
     * Retorna o Ambiente que é acessado saindo deste ambiente pela direcao.
     * se não tem nenhum Ambiente, retorna null.
     * @param direcao A direção da saida.
     * @return O Ambiente em dada direção.
     */
    public Sala getSaida(String direcao){
        return saidas.get(direcao);
    }

    /**
     * Return a description of the room’s exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */
    public String getSaidaString(){
        String strSaidas = "";
        for(String s: saidas.keySet()){
            strSaidas += s + " ";
        }

        return strSaidas;
    }

}
