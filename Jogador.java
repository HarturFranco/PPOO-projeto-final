import java.util.ArrayList;
import java.security.InvalidParameterException;

/**
 * Classe Jogador - um Jogador em um jogo adventure.
 * <p>
 * Esta classe eh parte da aplicacao "Fuga da Masmorra". "Fuga da Masmorral" eh
 * um jogo de aventura muito simples, baseado em texto.
 * <p>
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
    private boolean livre;
    private Sala salaAtual;
    private Mapa mapa;

    /**
     * @param mapa
     * @param salaInicio
     */
    public Jogador(Mapa mapa, Sala salaInicio) {
        this.chave = false;
        this.arma = true;
        this.vivo = true;
        this.livre = false;
        this.mapa = mapa;
        this.salaAtual = salaInicio;
    }

    /**
     *  Retorna a sala onde o jogadore está
     * @return objeto da sala onde o jogador está
     */
    public Sala getSalaAtual() {
        return salaAtual;
    }

    /**
     *  Retorna se o jogadore tem a chave
     * @return boleano dizendo se o jogador tem chave
     */
    public boolean temChave() {
        return chave;
    }

    /**
     *  Muda o jogador para que ele tenha a chave
     */
    public void pegaChave() {
        chave = true;
    }

    /**
     *  Retorna se o jogador pode atirar
     * @return boleano dizendo se o jogador pode atirar
     */
    public boolean temArma() {
        return arma;
    }

    /**
     *  Retorna se o jogador está vivo
     * @return boleano dizendo se o jogador está vivo
     */
    public boolean estaVivo() {
        return vivo;
    }

    /**
     *  Retorna se o jogador está livre da masmorra
     * @return boleano dizendo se o jogador está livre
     */
    public boolean estaLivre() {
        return this.livre;
    }
    
    /**
     *  Muda o estado de jogador para morto
     */
    public void setMorto() {
        vivo = false;
    }

    /**
     * executa a acao de entrar em uma sala adjacente
     *
     * @param codSala eh a sala em que se deseja atirar
     * @return narrativa ao atirar na sala
     */
    public String atirar(String codSala) {
        if (!mapa.existeSala(codSala)) {
            throw new InvalidParameterException("Essa sala não existe!");
        }

        Sala alvo = salaAtual.getSaida(codSala);

        if (alvo == null) {
            return "Você não tem acesso a essa sala.";
        } else if (!temArma()) {
            return "A sua arma não tem mais balas";
        } else {
            arma = false;
            return alvo.atirarNaSala(this);
        }
    }

    /**
     * executa a acao de entrar em uma sala adjacente
     *
     * @param codSala eh a sala em que se deseja entrar
     * @return narrativa ao entrar na sala
     */
    public String entrar(String codSala) {
        if (!mapa.existeSala(codSala)) {
            throw new InvalidParameterException("Essa sala não existe!");
        }

        Sala proximaSala = salaAtual.getSaida(codSala);

        if (proximaSala == null) {
            throw new InvalidParameterException("Você não tem acesso a essa sala!");
        }

        mapa.moveMarcador(salaAtual.getCodigo(), codSala);
        salaAtual = proximaSala;
        return salaAtual.entrarNaSala(this);
    }

    /**
     * marca a sala no mapa
     *
     * @param codSala codigo da sala a ser marcada
     */
    public void marcarSala(String codSala) {
        mapa.marcar(codSala);
    }

    /**
     * desmarca a sala no mapa
     *
     * @param codSala codigo da sala a ser desmarcada
     */
    public void desmarcarSala(String codSala) {
        mapa.desmarcar(codSala);
    }

    /**
     * metodo retorna as salas marcadas
     *
     * @return Salas do mapa marcadas pelo jogador
     */
    // TODO - seria legal retornar uma string?
    public ArrayList<String> getMarcadas() {
        return this.mapa.getSalasMarcadas();
    }

    /**
     * metodo retorna a arte do mapa do jogador
     *
     * @return a arte do mapa
     */
    // TODO - seria legal retornar uma string?
    public String getMapa() {
        return mapa.getMapa();
    }

    /**
     * executa acao de fulga.
     *
     * @return boolean com valor true se o jogador tem chave e esta na sala da saida
     */
    public String fugir() {
        // TODO - Usar Excecao?
        if (temChave()) {
            if (salaAtual instanceof SalaSaida) {
                this.livre = true;
                return "Você conseguiu Sair da Masmorra, parabéns!!!!";
            }
            return "Você não está na sala de saida!";
        }
        return "Você não possui a chave!";
    }


}
