/**
 * Classe SalaMonsto - uma sala perigosa em um jogo adventure.
 * <p>
 * Esta classe eh parte da aplicacao "Fuga da Masmorra".
 * "Fuga da Masmorral" eh um jogo de aventura muito simples, baseado em texto.
 * <p>
 * A classe "SalaMonstro" é a classe que define um dos ambientes de perigo
 * em que o jogador pode entrar. Nela há um monstro que emite uma
 * respiração monstruosa e, com o qual o jogador tem que lidar antes de entrar.
 *
 * @author Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */

public class SalaMonstro extends Sala {
    private boolean monstro;

    public SalaMonstro(String descricao) {
        super(descricao);
        this.monstro = true;
    }

    /**
     * @return String com o sons ou brisas que essa sala leva às suas salas vizinhas
     * nesse caso, a sala pode conter um monstro vivo, e então reproduz
     * uma respiração monstruosa, ou conter um monstro morto,
     * e então não reproduz nenhum som.
     */
    public String getSom() {
        if (this.monstro)
            return "-Você consegue ouvir uma respiração mostruosa vinda de alguma das salas vizinhas, mas você não sabe ao certo de qual.\n";
        else
            return "";
    }

    /**
     * @param jogador objeto do jogador que entrará na sala
     * @return String com o que acontece ao entrar na sala.
     * nesse caso, a sala pode conter um monstro vivo, e então
     * devorando o jogador ao entrar ou conter um monstro morto,
     * que não apresenta risco ao jogador e o dá a chave da saída.
     */
    @Override
    public String entrarNaSala(Jogador jogador) {
        if (this.monstro) {
            jogador.setMorto();
            return "-Você dá passos firmes em direção à sala " + this.getCodigo() + ". \nPassando por um corredor escuro, você encontra a próxima sala, mas quando nela chega já é tarde demais.\n" +
                    "um monstro te pega você, te come legal e cê tá mortão agora.\n";
        } else if (!jogador.temChave()) {
            jogador.pegaChave();
            return super.entrarNaSala(jogador) + "-Você vê uma enorme criatura, inconsciente, no chão. \nAo lado dela, há uma chave, a qual você guarda, na esperança que ela possa ser de ajuda.\n";
        } else {
            return super.entrarNaSala(jogador) + "-Você vê uma enorme criatura, inconsciente, no chão.\n";
        }
    }

    /**
     * @param jogador objeto do jogador que entrará na sala
     * @return String com o que acontece ao atirar em direção à sala.
     * nesse caso, a sala contem um monstro vivo e, ao atirar nela, o
     * jogador o mata e torna a sala segura para a entrada.
     */
    @Override
    public String atirarNaSala(Jogador jogador) {
        this.monstro = false;
        return "-Você atira às cegas em direção a porta da " + this.getCodigo() + ". \nVocê ouve um terrível urro de dor seguido da respiração monstruosa que passa de ofegante, para fraca, para inexistente em poucos minutos. \nAgora, não há mais som nenhum vindo daquela sala.\n";
    }
}
