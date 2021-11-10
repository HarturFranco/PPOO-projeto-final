import java.util.ArrayList;
import java.security.InvalidParameterException;

/**
 * Classe Jogador - um Jogador em um jogo adventure.
 *
 * Esta classe eh parte da aplicacao "Fuga da Masmorra". "Fuga da Masmorral" eh
 * um jogo de aventura muito simples, baseado em texto.
 *
 * A classe "Jogador" é a classe que define o jogador, eh nela que fica os dados
 * da sala atual, dois atributos do tipo booleano arma indica se o jogador tem a
 * arma usada para matar o monstro e chave indica se o jogador possui a chave
 * para fugir da masmorra.
 *
 * @author Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */

public class Jogador {

    private boolean vivo;
    private boolean chave;
    private boolean arma;
    private Sala salaAtual;
    private Mapa mapa;

    public Jogador(Mapa mapa) {
        this.chave = false;
        this.arma = true;
        this.vivo = true;
        this.mapa = mapa;
        // TODO - Receber salaAtual?
        this.salaAtual = null;
    }

    // TODO - Mudar IrAmbiente para Classe Jogador?
    public void setSalaAtual(Sala salaAtual) {
        this.salaAtual = salaAtual;
    }

    // TODO - Retornar Copia do objeto para manter encapsulamento?
    public Sala getSalaAtual() {
        return salaAtual;
    }

    public boolean temChave() {
        return chave;
    }

    public boolean temArma() {
        return arma;
    }

    public boolean estaVivo() {
        return vivo;
    }

    public String atirar(String direcao) {
        if (!mapa.existeSala(direcao)) {
            throw new InvalidParameterException("Essa sala não existe!");
        }

        Sala alvo = salaAtual.getSaida(direcao);

        if (alvo == null) {
            return "Você não tem acesso a essa sala.";
        } else if (!arma) {
            return "A sua arma não tem mais balas";
        } else {
            arma = false;
            return alvo.atirarNaSala();
        }
    }

    public String entrar(String direcao) {
        if (!mapa.existeSala(direcao)) {
            throw new InvalidParameterException("Essa sala não existe!");
        }

        Sala proximaSala = salaAtual.getSaida(direcao);

        if (proximaSala == null) {
            return "Você não tem acesso a essa sala.";
        } else {
            salaAtual = proximaSala;
            return salaAtual.entrarNaSala();
        }
    }

    public void marcarSala(String codSala) {
        mapa.marcar(codSala);
    }

    public void desmarcarSala(String codSala) {
        mapa.desmarcar(codSala);
    }

    public ArrayList<String> getMarcadas() {
        return this.mapa.getSalasMarcadas();
    }

    public boolean fugir() {
        // TODO - Usar Excecao?
        if (temChave()) {
            if (salaAtual instanceof SalaSaida) {
                return true;
            }
        }
        return false;
    }
}
