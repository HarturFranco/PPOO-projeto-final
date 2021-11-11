/**
 *
 */
public enum PalavraComando {
    // Um valor para cada palavra de comando
    // com a string da interface correspondente.
    IR("ir"), SAIR("sair"), AJUDA("ajuda"), FUGIR("fugir"), ATIRAR("atirar"), MARCAR("marcar"), DESMARCAR("desmarcar"), DESCONHECIDO("?");

    // A string comando
    private String stringComando;

    /**
     * Inicializa com a string comando correspondente.
     *
     * @param stringComando A String Com o Comando
     */
    PalavraComando(String stringComando) {
        this.stringComando = stringComando;
    }

    /**
     * @return A PalavraComando como String.
     */
    public String toString() {
        return stringComando;
    }
}
