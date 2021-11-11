/**
 * Classe SalaBuraco - uma sala perigosa em um jogo adventure.
 *
 * Esta classe eh parte da aplicacao "Fuga da Masmorra".
 * "Fuga da Masmorral" eh um jogo de aventura muito simples, baseado em texto.  
 *
 * A classe "SalaBuraco" é a classe que define um dos ambientes de perigo
 *  em que o jogador pode entrar. Nela há um buraco que solta uma leve brisa
 *  e irá terminar o jogo caso o jogador tente acessar a sala.
 * 
 * @author  Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */
 
public class SalaBuraco extends Sala
{    
    public SalaBuraco(String codigo){
        super(codigo);
    }

    /**
     * @return String com o sons ou brisas que essa sala leva às suas salas vizinhas
     * nesse caso,  sala contem um  buraco que solta uma 
     *  leve brisa até a salas vizinhas.
     */
    public String getSom(){
        return "-Você sente uma suave brisa vinda de alguma das salas vizinhas, mas você não sabe ao certo de qual.\n";
    }
    
    /**
     * @param jogador objeto do jogador que entrará na sala
     * @return String com o que acontece ao entrar na sala.
     * nesse caso, já o corredor em direção à sala contem uma
     *  um profundo buraco, onde o jogador cai e pede o jogo
     *  caso entre nessa sala.
     */
    @Override
    public String entrarNaSala(Jogador jogador) {
        jogador.setMorto();
        return "-Você dá passos firmes em direção à sala "+this.getCodigo()+
            ". \nPassando por um corredor escuro, em um momento o chão parece sumir debaixo dos seus pés.\n"+
            "Quando você se dá conta do ocorrido,  já está em queda livre.\n" +
            "Enquanto você tem seu último sonho com a liberdade, tão de repente quanto ela começou, a queda termina.\n"+
            "E agora só resta escuridão.\n";
    }
}
