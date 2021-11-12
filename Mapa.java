import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.security.InvalidParameterException;

/**
 * Classe Mapa - faz as operacoes necessarias ao mapa e guarda suas informações.
 * 
 * Esta classe eh parte da aplicacao "Fuga da Masmorra". "Fuga da Masmorral" eh
 * um jogo de aventura muito simples, baseado em texto.
 * 
 * A classe "Mapa" é a classe que define o mapa onde o jogador poderá se
 * movimentar. Nela são definidas todas as salas e as salas
 * marcadas a partir de uma aqruivo de texto e como elas são 
 * mostradas na interface.
 *
 * @author Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */

public class Mapa {
    private char[][] mapa;
    private Sala salaInicio;

    private HashMap<String, Sala> todasSalas;
    private ArrayList<String> salasMarcadas;
    private HashMap<String, int[]> cordenadas;


    /**
     * Inicia os atributos do mapa e lê as informações de um arquivo de texto
     * 
     * @param nomeMapa nome do arquivo de texto a ser lido
     * @throws exception - joga as exceções de IO e demais
     *  para serem mostradas na GUI.
     * 
     */
    public Mapa(String nomeMapa) throws Exception{
        this.todasSalas = new HashMap<>();
        this.salasMarcadas = new ArrayList<>();
        this.cordenadas = new HashMap<>();
        this.iniciarMapa(nomeMapa);
    }

    /**
     * @param nomeMapa nome do arquivo de texto a ser lido
     * @throws exception - joga as exceções de IO e demais
     *  para serem mostradas na GUI.
     * Lê e define o mapa de um arquivo txt 
     */
    private void iniciarMapa(String nomeMapa) throws Exception {
        try (BufferedReader arq = new BufferedReader(new FileReader(nomeMapa))) {
            
            //lê a quantidade de salas desse mapa
            String linha = arq.readLine();
            int numeroSalas = Integer.parseInt(linha);
            arq.mark(numeroSalas * 100);


            //lê e cria salas com seus tipos derivados
            for (int i = 1; i <= numeroSalas; i++) {
                linha = arq.readLine();
                String[] id = linha.split(" ");

                arq.readLine();

                linha = arq.readLine();
                String[] tipo = linha.split(" ");

                Sala sala;
                String tipoTratado = tipo.length > 1 ? tipo[1] : "";

                switch (tipoTratado) {
                    case "inicio":
                        sala = new Sala(id[1]);
                        salaInicio = sala;
                        break;
                    case "saida":
                        sala = new SalaSaida(id[1]);
                        break;
                    case "monstro":
                        sala = new SalaMonstro(id[1]);
                        break;
                    case "buraco":
                        sala = new SalaBuraco(id[1]);
                        break;
                    case "armadilha":
                        sala = new SalaArmadilha(id[1]);
                        break;
                    default:
                        sala = new Sala(id[1]);
                }

                todasSalas.put(id[1], sala);

                arq.readLine();
            }
            
            //volta o ponteiro de leitura para a linha marcada
            arq.reset();

            //liga as salas a suas adjacentes
            for (int i = 1; i <= numeroSalas; i++) {
                linha = arq.readLine();
                String[] id = linha.split(" ");

                linha = arq.readLine();
                String[] adjacentes = linha.split(" ");

                arq.readLine();

                for (int j = 1; j < adjacentes.length; j++) {
                    if (i >= 1)
                        todasSalas.get(id[1]).adicionarSaida(adjacentes[j], todasSalas.get(adjacentes[j]));
                }
                arq.readLine();
            }
            linha = arq.readLine();

            //procura pelo indicador de mapa visual
            while (!linha.equals("-mapa-") && linha != null) {
                linha = arq.readLine();
            }
            
            //Lê mapa visual, caso exista
            if(linha != null){
                //lê número de linhas do mapa
                linha = arq.readLine();
                String[] linhas = linha.split(" ");
                int n0 = Integer.parseInt(linhas[0]);

                mapa = new char[n0][];
                arq.readLine();
                linha = arq.readLine();

                //lê o mapa visual
                for (int i = 0; i < n0; i++) {
                    mapa[i] = linha.toCharArray();
                    linha = arq.readLine();
                }

                //lê as cordenadas das salas para alteração
                int[] aux;
                for (int i = 0; i < numeroSalas; i++) {
                    linha = arq.readLine();

                    aux = new int[2];
                    linhas = linha.split(" ");
                    aux[0] = Integer.parseInt(linhas[2]);
                    aux[1] = Integer.parseInt(linhas[3]);
                    cordenadas.put(linhas[1], aux);
                }
            }

        } catch (IOException e) {
            throw new IOException("Erro na leitura do mapa.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * Troca um caracter por outro do mapa visual
     *
     * @param ant caracter a ser trocado
     * @param nov caracter que substituirá
     * @param x valor da cordenada x do mapa
     * @param y valor da cordenada y do mapa
     */
    private void trocaUnic(char ant, char nov, int x, int y) {
        if (mapa[x][y] == ant)
            mapa[x][y] = nov;
    }

    /**
     * Troca todos caracteres iguais adjacentes
     *  por outros do mapa visual
     *
     * @param ant caracter a ser trocado
     * @param nov caracter que substituirá
     * @param x valor da cordenada x do mapa
     * @param y valor da cordenada y do mapa
     */
    private void trocaRecurs(char ant, char nov, int x, int y) {
        if (mapa[x][y] == ant) {
            mapa[x][y] = nov;
            trocaRecurs(ant, nov, x + 1, y);
            trocaRecurs(ant, nov, x, y + 1);
            trocaRecurs(ant, nov, x - 1, y);
            trocaRecurs(ant, nov, x, y - 1);
        }
    }


    /**
     * Retorna o mapa visual pra apresentação
     *
     * @return String com o mapa visual já
     *  configurado para apresentação em html
     */
    public String getMapa() {
        String s = "<html><pre>";
        for (int i = 0; i < mapa.length; i++) {
            s += new String(mapa[i]) + "<br>";
        }
        return s + "</pre></html>";
    }

    /**
     * Retorna a sala onde o jogador começa
     *
     * @return objeto da sala onde o jogador
     * começa.
     */
    public Sala getSalaInicio() {
        return salaInicio;
    }


    /**
     * Diz se uma determinada sala existe no mapa
     * 
     * @param codSala código da sala procurada
     * @return boleano dizendo se a sala está 
     * no mapa
     */
    public boolean existeSala(String codSala) {
        if (todasSalas.get(codSala) != null)
            return true;
        return false;
    }

    /**
     * Marca uma sala como suspeita/perigosa.
     *  Uma vez marcada a sala vira suspeita(╱)
     *  uma sala suspeita marcada novamente
     *  se torna uma sala perigosa(╳) no mapa visual.
     * 
     * @param codSala código da sala a ser marcada
     */
    public void marcar(String codSala) {
        if (!existeSala(codSala))
            throw new InvalidParameterException("Essa sala não existe!");

        int[] aux = cordenadas.get(codSala);

        if (mapa[aux[0]][aux[1] - 1] == '╱')
            trocaRecurs('╱', '╳', aux[0], aux[1] - 1);
        else {
            trocaRecurs(' ', '╱', aux[0], aux[1] - 1);
            salasMarcadas.add(codSala);
        }
    }


    /**
     * Desmarca uma sala como suspeita/perigosa.
     *  Ao ser desmarcada, uma sala suspeita(╱) ou
     *  perigosa(╳) volta ao normal(espaços em branco)
     *  no mapa visual.
     * 
     * @param codSala código da sala a ser marcada
     */
    public void desmarcar(String codSala) {
        if (!existeSala(codSala))
            throw new InvalidParameterException("Essa sala não existe!");

        int[] aux = cordenadas.get(codSala);

        if (mapa[aux[0]][aux[1] - 1] == '╳')
            trocaRecurs('╳', ' ', aux[0], aux[1] - 1);
        else
            trocaRecurs('╱', ' ', aux[0], aux[1] - 1);

        salasMarcadas.remove(codSala);
    }


    /**
     * Troca o marcador que representa o jogador(☺)
     *  de uma sala para a outra no mapa visual.
     * 
     * @param codSalaAnt código da sala
     *  de onde o jogador vem
     * @param codSalaNov código da sala
     *  para onde o jogador vai
     */
    public void moveMarcador(String codSalaAnt, String codSalaNov) {
        int[] aux = cordenadas.get(codSalaAnt);
        trocaUnic('☺', ' ', aux[0], aux[1]);

        aux = cordenadas.get(codSalaNov);
        trocaUnic(' ', '☺', aux[0], aux[1]);
        trocaUnic('╱', '☺', aux[0], aux[1]);
        trocaUnic('╳', '☺', aux[0], aux[1]);
    }

    /**
     *  Retorna as salas que estão como marcadas pelo jogador
     * @return 
     */
    public ArrayList<String> getSalasMarcadas() {
        return salasMarcadas;
    }
}
