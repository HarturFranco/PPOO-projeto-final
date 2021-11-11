import java.util.HashMap;

/**
 * Classe Sala - uma sala em um jogo adventure.
 *
 * Esta classe eh parte da aplicacao "Fuga da Masmorra". "Fuga da Masmorra" eh
 * um jogo de aventura muito simples, baseado em texto.
 *
 * A classe "Sala" é a classe que define os ambientes em que o jogador irá
 * navegar. Uma sala pode ter uma ou mais salas vizinhas, as quais podem se
 * mostrar perigosas ou não. A classe "Sala" diz respeito as salas báscias, sem
 * perigo e que não são a saída da masmorra, outros tipos de salas específicas
 * herdam dela.
 * 
 * @author Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */
public class Sala {
    private String descricao;
    HashMap<String, Sala> saidas;

    /**
     * Cria ua sala com a "descricao" passada. Inicialmente, ela nao tem saidas.
     * "descricao" eh algo como "Sala 01" ou "Sala 02"
     * 
     */
    public Sala(String descricao) {
        this.descricao = descricao;
        saidas = new HashMap<>();
    }

    /**
     * Define uma saida desse ambiente
     * 
     * @param cordenada, cordenada de uma saida.
     * @param adjacente  O ambiente na direcao dada
     */
    public void adicionarSaida(String cordenada, Sala adjacente) {
        saidas.put(cordenada, adjacente);
    }

    /**
     * @return A descricao da sala.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a sala para qual o jogador vai. se essa sala não é acssível, retorna
     * null.
     * 
     * @param cordenada, cordenada da sala que o jogador quer entrar.
     * @return A sala para qual o jogador irá.
     */
    public Sala getSaida(String cordenada) {

        for (String s : saidas.keySet()) {
            System.out.println(s);
        }
        return saidas.get(cordenada);
    }

    /**
     * 
     * @return A descrição das salas adjacentes. por exemplo 'adjacentes: sala01,
     *         sala02, sala03'".
     */

    public String getSaidaString() {
        String strSaidas = "";
        for (String s : saidas.keySet()) {
            strSaidas += s + " ";
        }

        return strSaidas;
    }

    /**
     * @return String com o sons ou brisas que essa sala leva às suas salas vizinhas
     *         por exemplo "uma brisa fria" nesse caso, a sala não produz nada de
     *         anormal, então retorna uma string vazia.
     */
    public String getSom() {
        return "";
    }

    /**
     * @return String mostrando o que o jogador vê, ouve e sente ao entrar nessa
     *         sala. nesse caso, sendo uma sala segura ele vê as outras salas e ouve
     *         possíveis sons/ brisas vindos das salas vizinhas.
     */
    public String entrarNaSala(Jogador jogador) {
        String s = "-Você dá passos firmes em direção à sala " + this.getDescricao()
                + ". \nPassando por um corredor escuro, consegue chegar ao outro lado.\n";
        s = s + "-Ao chegar, você percebe que tochas iluminam as portas das salas " + this.getSaidaString() + "\n";

        Sala sala;
        for (String dir : saidas.keySet()) {
            sala = this.saidas.get(dir);
            if (sala != null)
                s += sala.getSom();
        }
        return s;
    }

    /**
     * @return String com o que acontece ao atirar em direção à sala. nesse caso, a
     *         sala não contem o monstro vivo e, ao atirar nela, o jogador gasta sua
     *         última bala e atrai o monstro para si com o barulho do tiro, fazendo
     *         com que o jogo acabe.
     */
    public String atirarNaSala(Jogador jogador) {
        jogador.setMorto();
        return "-Você atira às cegas em direção a porta da sala " + this.getDescricao()
                + ".\nApós o estouro da arma, seus ovidos zunem por um breve momento.\n "
                + "Sua audição volta logo a tempo de ouvir o os de cascos pesados rapidamente se aproximando de você.\n"
                + "Mal tendo tempo de localizar de qual porta vem o som, você é atingido pelas costas por uma terrível besta e perde imediatamente a consiência.";

    }
}
