package operacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import modelo.Nota;
import modelo.Usuario;

public class NotasOp {
  
  public static void populaNotas(Usuario u) throws IOException {
    File dir = new File("./"+u.getEmail());
    if(dir.exists() && dir.isDirectory()) {
      File[] directoryListing = dir.listFiles();
      if (directoryListing != null) {
        u.limpaNotas();
        for (File nota : directoryListing) {
          Nota n = new Nota();
          n.setNome(nota.getName());
          BufferedReader br;
          br = new BufferedReader(new FileReader(nota));
          String st, cont = ""; 
          while ((st = br.readLine()) != null) 
            cont+=st+"\n";
          br.close();
          n.setConteudo(cont);
          u.adicionaNota(n);
        }
      }
    }
  }

  public static List<String> nomesNotas(Usuario usuario) {
    List<Nota> notas = usuario.getNotas();
    List<String> lNomes = new ArrayList<String>();
    for(Nota n : notas) {
      lNomes.add(n.getNome());
    }
    return lNomes;
  }
  
  public static String conteudoNota(Usuario u, String nome) {
    try {
      populaNotas(u); // para atualizar, caso houve alguma alteração
    } catch (IOException e) {
      e.printStackTrace();
    }
    for(Nota n : u.getNotas()) {
      if(n.getNome().equals(nome))
        return n.getConteudo();
    }
    return "";
  }
  
  public static void atualizar(Usuario u, String nome, String conteudoNovo) throws IOException {
    File dir = new File("./"+u.getEmail());
    if(dir.exists() && dir.isDirectory()) {
      File[] directoryListing = dir.listFiles();
      if (directoryListing != null) {
        for (File nota : directoryListing) {
          if(nota.getName().equals(nome)) {
            PrintWriter writer = new PrintWriter(nota);
            writer.print(conteudoNovo);
            writer.close();
          }
        }
      }
    }
  }

  public static void salvar(Usuario logado, String nome, String conteudo) throws IOException {
    File dir = new File("./" + logado.getEmail());
    if(dir.exists() && dir.isDirectory()) {
      File f = new File(dir.getAbsolutePath() + File.separator + nome);
      f.getParentFile().mkdirs(); 
      f.createNewFile();
      PrintWriter writer = new PrintWriter(f);
      writer.print(conteudo);
      writer.close();
    }
  }

}
