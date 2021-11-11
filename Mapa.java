import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.security.InvalidParameterException;

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
  private char[][] mapa;
  private Sala salaInicio;

  private HashMap<String, Sala> todasSalas;
  private ArrayList<String> salasMarcadas;
  private HashMap<String, int[]> cordenadas; 

  public Mapa(String nomeMapa) {
    this.todasSalas = new HashMap<>();
    this.salasMarcadas = new ArrayList<>();
    this.cordenadas = new HashMap<>();

    this.iniciarMapa(nomeMapa);
  }

  /**
   * Cria todos as Salas e liga as saidas deles
   */
  private void iniciarMapa(String nomeMapa) {
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

        for (int j = 1; j < adjacentes.length; j++) {
          if (i >= 1)
            todasSalas.get(id[1]).adicionarSaida(adjacentes[j], todasSalas.get(adjacentes[j]));

        }

        linha = arq.readLine();
      }

      for (String key : todasSalas.keySet())
        System.out.println(key + "-> " + todasSalas.get(key).getSaidaString());
      linha = arq.readLine();
      
      
        while(!linha.equals("-mapa-")){
            linha = arq.readLine();
            System.out.println(linha);
        }
            
        linha = arq.readLine();
        String[] linhas = linha.split(" ");
        int n0 = Integer.parseInt(linhas[0]);
        
        mapa = new char[n0][];
        linha = arq.readLine();
        linha = arq.readLine();
        
        
        for(int i = 0; i < n0; i++){
            mapa[i] = linha.toCharArray();
            linha = arq.readLine();
        }
        
        int[] aux;
        for(int i = 0; i < numeroSalas; i++){
            linha = arq.readLine();
            
            aux = new int[2];
            linhas = linha.split(" ");
            aux[0] =  Integer.parseInt(linhas[2]);
            aux[1] =  Integer.parseInt(linhas[3]);
            System.out.println("SALA: "+linhas[1]+"->"+linhas[2]+","+linhas[3]);            
            cordenadas.put(linhas[1], aux);
        }
        
    } catch (IOException e) {
      System.out.println("Erro na leitura do mapa");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


    private void trocaRecurs(char ant, char nov, int x, int y){
        if(mapa[x][y] == ant){
            mapa[x][y] = nov;
            trocaRecurs(ant,  nov, x+1, y);
            trocaRecurs(ant,  nov, x, y+1);
            trocaRecurs(ant,  nov, x-1, y);
            trocaRecurs(ant,  nov, x, y-1);
        }
    }

  public String getMapa() {
    String s = "<html><pre>";
    for(int i = 0; i < mapa.length; i++)
    {
        System.out.println(mapa[i]);
        s += new String(mapa[i]) + "<br>";
    }
    return s+"</pre></html>";
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
    if(!existeSala(codSala))
        throw new InvalidParameterException("Essa sala não existe!");
        
    int[] aux = cordenadas.get(codSala);
    
    if(mapa[aux[0]][aux[1]] == '╱')
        trocaRecurs('╱', '╳', aux[0], aux[1]);
    else{
        trocaRecurs(' ', '╱', aux[0], aux[1]);
        salasMarcadas.add(codSala);
    }
  }

  public void desmarcar(String codSala) {
    if(!existeSala(codSala))
        throw new InvalidParameterException("Essa sala não existe!");
        
    int[] aux = cordenadas.get(codSala);
    
    if(mapa[aux[0]][aux[1]] == '╳')
        trocaRecurs('╳', ' ', aux[0], aux[1]);
    else
        trocaRecurs('╱', ' ', aux[0], aux[1]);
    
    salasMarcadas.remove(codSala);
  }

  public ArrayList<String> getSalasMarcadas() {
    return salasMarcadas;
  }
}
