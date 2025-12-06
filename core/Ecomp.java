package core;

import model.*;
import repository.*;

import java.util.List;

public class Ecomp {

  ProjetoRepository projetoRepository;

  public Ecomp() {
    projetoRepository = new ProjetoRepository("data/projeto.csv");
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
      return "contratos/" + "id/" + nome + ".pdf";

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

}

