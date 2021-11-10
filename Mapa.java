import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe Mapa - faz as operacoes necessarias ao mapa.
 *
 * Esta classe eh parte da aplicacao "Fuga da Masmorra". "Fuga da Masmorral" eh
 * um jogo de aventura muito simples, baseado em texto.
 *
 * A classe "Mapa" é a classe que define o mapa onde o jogador poderá se
 * movimentar. Nela é definido a sala inicial, todas as salas e as salas
 * marcadas a partir de uma aqruivo de texto.
 * 
 * @author Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */

public class Mapa {
  private String mapa;
  private Sala salaInicio;

  private HashMap<String, Sala> todasSalas;
  private ArrayList<String> salasMarcadas;

  public Mapa(String nomeMapa) {
    this.mapa = "";
    this.todasSalas = new HashMap<>();

    this.iniciarMapa(nomeMapa);
  }

  /**
   * Cria todos as Salas e liga as saidas deles
   */
  private void iniciarMapa(String nomeMapa) {
    todasSalas = new HashMap<>();
    try (BufferedReader arq = new BufferedReader(new FileReader(nomeMapa))) {

      String linha = arq.readLine();
      int numeroSalas = Integer.parseInt(linha);
      arq.mark(numeroSalas * 100);

      // cria salas com seus tipos derivados
      for (int i = 1; i <= numeroSalas; i++) {
        linha = arq.readLine();
        String[] id = linha.split(" ");

        linha = arq.readLine();
        // String[] adjacentes = linha.split(" ");

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

        linha = arq.readLine();
      }

      arq.reset();

      for (int i = 1; i <= numeroSalas; i++) {
        linha = arq.readLine();
        String[] id = linha.split(" ");

        linha = arq.readLine();
        String[] adjacentes = linha.split(" ");

        linha = arq.readLine();

        for (int j = 0; j < adjacentes.length; j++) {
          if (i >= 1)
            todasSalas.get(id[1]).adicionarSaida(adjacentes[j], todasSalas.get(adjacentes[j]));

        }

        linha = arq.readLine();
      }

      for (String key : todasSalas.keySet()) {
        System.out.println(key + "-> " + todasSalas.get(key).getSaidaString());
      }

      linha = arq.readLine();
      while (linha != null) {
        mapa += linha;

        linha = arq.readLine();
      }

    } catch (IOException e) {
      System.out.println("Erro na leitura do mapa");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public String getMapa() {
    return mapa;
  }

  public Sala getSalaInicio() {
    return salaInicio;
  }

  public boolean existeSala(String direcao) {
    if (todasSalas.get(direcao) != null)
      return true;
    return false;
  }

  public void marcar(String codSala) {
    salasMarcadas.add(codSala);
  }

  public void desmarcar(String codSala) {
    salasMarcadas.remove(codSala);
  }

  public ArrayList<String> getSalasMarcadas() {
    return salasMarcadas;
  }
}