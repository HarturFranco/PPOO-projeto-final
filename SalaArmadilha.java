/**
 * Classe SalaArmadilha - uma sala perigosa em um jogo adventure.
 *
 * Esta classe eh parte da aplicacao "Fuga da Masmorra".
 * "Fuga da Masmorral" eh um jogo de aventura muito simples, baseado em texto.  
 *
 * A classe "SalaArmadilha" é a classe que define um dos ambientes de perigo
 *  em que o jogador pode entrar. Nela há uma armadilha que emite sons de 
 *  maquinário e irá terminar o jogo caso o jogador tente acessá-la.
 * 
 * @author  Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */

public class SalaArmadilha extends Sala
{    
    public SalaArmadilha(String descricao){
        super(descricao);
    }
    
    /**
     * @return String com o sons ou brisas que essa sala leva às suas salas vizinhas
     * nesse caso, q sala contem uma armadilha que reproduz
     *  sons de engrenagens para as salas vizinhas.
     */
    public String getSom(){
        return "Você consegue ouvir sons de algum tipo de maquinário vindo de alguma das salas vizinhas, mas você não sabe ao certo de qual.\n";
    }
    
    /**
     * @return String com o que acontece ao entrar na sala.
     * nesse caso, o corredor em direção à sala contem uma
     *  armadilha que dispara na entrada do jogador
     *  e ao ser disparada mata o jogador e termina o jogo.
     */
    @Override
    public String entrarNaSala(Jogador jogador) {
        jogador.setMorto();
        return "Você dá passos firmes em direção à "+this.getDescricao()+". \n Passando por um corredor escuro, você pisa em falso e sente algo se mover embaixo de seus pés.\n"+
            "Sem tempo de reagir, você é atingido em seu peito por uma longa estaca de madeira, vai ao chão e dá os ultimos suspiros sob som das engrenagens da armadilha se movimentando.\n";
    }
    
}
