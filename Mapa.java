import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Classe Mapa - faz as operacoes necessarias ao mapa.
 *
 * Esta classe eh parte da aplicacao "Fuga da Masmorra". "Fuga da Masmorral" eh
 * um jogo de aventura muito simples, baseado em texto.
 *
 * A classe "Mapa" é a classe que define um dos ambientes de perigo em que o
 * jogador pode entrar. Nela há um monstro que emite uma respiração monstruosa
 * e, com o qual o jogador tem que lidar antes de entrar.
 * 
 * @author Alexandre Rabello, Arthur Franco, Felipe Godoi e João Paulo Paiva.
 * @version 2021.11.06
 */

public class Mapa {
  private String mapa;

  public Mapa() {
    this.mapa = "";
  }

  public String getMapa() {
    return mapa;
  }

  public HashMap<String, Sala> iniciarMapa(String nomeMapa) {
    HashMap<String, Sala> todasSalas = new HashMap<>();

    try (BufferedReader arq = new BufferedReader(new FileReader(nomeMapa))) {
      String linha = arq.readLine();
      int numeroSalas = Integer.parseInt(linha);

      // Cria todas a salas
      for (int i = 1; i <= numeroSalas; i++) {
        Sala sala = new Sala(i + "");
        todasSalas.put(i + "", sala);
      }

      // liga as salas com outras
      for (int i = 1; i <= numeroSalas; i++) {
        linha = arq.readLine();
        String numeroSala = linha;

        linha = arq.readLine();
        String[] adjacentes = linha.split(" ");

        linha = arq.readLine();
        String tipo = linha;

        for (int j = 0; j < adjacentes.length; j++) {
          todasSalas.get(numeroSala).adicionarSaida(adjacentes[j], todasSalas.get(j + ""));
        }

        // casting de salas
        Sala sala;
        switch (tipo) {
        case "saida":
          sala = new SalaSaida("");
          todasSalas.put(numeroSala, todasSalas.get(numeroSala).getClass().cast(sala));
          break;
        case "monstro":
          sala = new SalaMonstro("");
          todasSalas.put(numeroSala, todasSalas.get(numeroSala).getClass().cast(sala));
          break;
        case "armadilha":
          sala = new SalaArmadilha("");
          todasSalas.put(numeroSala, todasSalas.get(numeroSala).getClass().cast(sala));
          break;
        case "buraco":
          sala = new SalaBuraco("");
          todasSalas.put(numeroSala, todasSalas.get(numeroSala).getClass().cast(sala));
          break;
        }
      }

      linha = arq.readLine();
      while (linha != null) {
        mapa += linha;
        linha = arq.readLine();
      }

      // System.out.println(mapa);

    } catch (IOException e) {
      System.out.println("Erro na leitura do mapa");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return todasSalas;
  }
}