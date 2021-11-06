/**
 * Classe SalaBuraco - uma sala perigosa em um jogo adventure.
 *
 * Esta classe eh parte da aplicacao "Fuga da Masmorra".
 * "Fuga da Masmorral" eh um jogo de aventura muito simples, baseado em texto.  
 *
 * A classe "SalaBuraco" é a classe que define um ambiente seguro
 *  em que o jogador pode entrar. Nela não há perigo, mas ela se destaca 
 *  das salas básicas por nela haver um túnel que leva à saída.
 * 
 * @author  Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */

public class SalaSaida extends Sala
{    
    public SalaSaida(String descricao){
        super(descricao);
    }
    
    /**
     * @return String mostrando o que o jogador vê, ouve e sente ao entrar nessa sala.
     * nesse caso, sendo uma sala segura ele vê as outras salas e ouve possíveis sons/ brisas
     *  vindos das salas vizinhas.
     * 
     * Mas, já que essa é a sala que leva à saída, o jogador também vê o túnel da saída,
     *  que o leva para completar o jogo e grade com cadeado
     *  que o impede de prosseguir sem a chave.
     */
    @Override
    public String entrarNaSala(){
        return super.entrarNaSala() +"Você pode ver, também, uma grade trancada com um grande cadeado, que, apesar de velho, parece bem maciço.\n "+
                    "Você consegue enxergar uma luz tênue, do que parece uma saída, vinda de um túnel atrás da grade.\n"+
                    "Essa pode ser sua chance para a liberdade caso encontre a chave do cadeado.\n";
    }
    
}

