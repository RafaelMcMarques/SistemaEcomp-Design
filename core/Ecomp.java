package core;

import model.*;
import repository.*;

import java.util.List;

import javax.management.Descriptor;

public class Ecomp {

  ProjetoRepository projetoRepository;
  EcomperRepository ecomperRepository;

  public Ecomp() {
    projetoRepository = new ProjetoRepository("data/projeto.csv");
    ecomperRepository = new EcomperRepository("data/ecomper.csv");
  }



  public void cadastrarCliente() {
    return;
  }

  public int cadastrarProjeto(String decricao, String dataInicio, String dataPrazo, String nomeCliente) {
    Projeto p = new Projeto();

    // TODO: criar cliente completo
    Cliente cliente = new Cliente();

    try {
      cliente.setNome(nomeCliente);
      p.setCliente(cliente);

      p.setDescricao(decricao);
      p.setDataInicio(dataInicio);
      p.setDataPrazo(dataPrazo);

      p = projetoRepository.insertNew(p);
      return p.getId();
    } catch (Exception e) {
      throw e;
    }

  }

  public String getListaProjetosStr() {
    List<Projeto> projetos = projetoRepository.getAll();

    StringBuilder sb = new StringBuilder();

    if (projetos.isEmpty()) {
      return "Nenhum projeto cadastrado!";
    }
    for (Projeto p : projetos) {
        sb.append(p.toString());
        sb.append("\n"); 
    }

    return sb.toString(); 

  }

  public String associarContrato(int id, String nome, String caminho) {
    Projeto p = projetoRepository.getById(id);

    try {
      p.setNomeContrato(nome);
      p.setCaminhosContrato(caminho);

      projetoRepository.update(p);
      return "contratos/" + id + "/" + nome + ".pdf";

    } catch (Exception e) {
      throw e; 
    }

  }

  public String getProjetosParaSelecao() {
    List<Projeto> projetos = projetoRepository.getAll();

    if (projetos.isEmpty()) {
      return "Nenhum projeto cadastrado!";
    }

    StringBuilder sb = new StringBuilder();
    for (Projeto p: projetos) {
      sb.append("Projeto ")
      .append(p.getId()).append(" -> ")
      .append("Descrição: \"").append(p.getDescricao()).append("\"");
      sb.append("| Cliente: \"").append(p.getCliente().getNome()).append("\"");
      sb.append("\n");
    }

    return sb.toString();
  }

  public boolean projetoExiste(int id) {
    return projetoRepository.getById(id) != null;
  }

  public void cadastrarEtapaProjeto(int projId, String cronograma, String status) {
    Etapa etapa = new Etapa();
    Projeto p = projetoRepository.getById(projId);

    try {
      etapa.setCronograma(cronograma);
      etapa.setStatus(status);

      p.addEtapa(etapa);

      projetoRepository.update(p);

    } catch (Exception e) {
      throw e;
    }
  }

  public int cadastrarMembro(String nome, String email, String cpf, String cargo) {
    Ecomper ecomper = new Ecomper();

    try {
      ecomper.setNome(nome); 
      ecomper.setCargo(cargo);
      ecomper.setCpf(cpf);
      ecomper.setEmail(email);

      ecomper = ecomperRepository.insertNew(ecomper);
      return ecomper.getId();
    } catch (Exception e) {
      throw e;
    }

  }

  public String getListaEcompersStr() {
    List<Ecomper> lista = ecomperRepository.getAll();

    if (lista == null || lista.isEmpty()) 
      return "Nenhum ecomper cadastrado!";

    StringBuilder sb = new StringBuilder();

    for (Ecomper e : lista) {
        sb.append(e.toString()).append("\n");
    }

    return sb.toString();
  }

  public String getDesenvolvedoresParaSelecao(int projId) {
    Projeto p = projetoRepository.getById(projId);
    List<Ecomper> ecompers = ecomperRepository.getAll();


    StringBuilder sb = new StringBuilder();
    for (Ecomper e: ecompers) {

      boolean jaTem = false;
      for (Ecomper devDoProj : p.getDesenvolvedores()) {
        if (devDoProj.getId() == e.getId())
          jaTem = true;
      }
      if (jaTem) continue;
       
      sb.append("Ecomper ")
      .append(e.getId()).append(" -> ")
      .append("Nome: \"").append(e.getNome()).append("\"");
      sb.append("| Cargo: \"").append(e.getCargo()).append("\"");
      sb.append("\n");
    }

    if (sb.toString() == "" || sb.toString().isBlank() || sb.toString().isEmpty()) {
      return "Nenhum ecomper disponivel!";
    }

    return sb.toString();
  }

  public void vincularDesenvolvedorProjeto(int projId, int devId) {
    System.out.println(projId + " contem " + devId);
    Desenvolvedor dev = Desenvolvedor.fromSuper(ecomperRepository.getById(devId));

    Projeto p = projetoRepository.getById(projId);
    p.addDesenvolvedor(dev); 

    projetoRepository.update(p);
  }

}

