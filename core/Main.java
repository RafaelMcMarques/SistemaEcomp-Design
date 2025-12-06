package core;

import java.util.*;

public class Main {
  private static Scanner sc = new Scanner(System.in);
  private static Ecomp ecomp = new Ecomp();
  
  public static void main(String[] args) {

    telaPrincipal();

  }

  public static void telaPrincipal() {
    System.out.println("\n\n==== Sistema Ecomp ====\n");
    System.out.println("1 - para exibir relatorio de projetos");
    System.out.println("2 - para cadastrar projeto");
    System.out.println("3 - para mostrar projetos para seleção");
    System.out.println("-1 para sair\n");

    int op = Integer.parseInt(sc.nextLine());


    switch (op) {
      case 1:
        exibirRelatorioProjetos();
        return;
      case 2:
        iniciarCadastroProjeto();
        return;
      case 3:
        mostrarProjetosParaSelecao();
        return;
      case -1:
        return;
      default:
        return;
    }
  }


  public static void exibirRelatorioProjetos() {
    System.out.println("RELATORIO DE PROJETOS:\n");
    System.out.println(ecomp.getListaProjetosStr());
    System.out.println("FIM DO RELATORIO DE PROJETOS\n");

    System.out.println("Digite qualquer tecla pra voltar ao menu");
    sc.nextLine();
    telaPrincipal();
    return;
  }

  public static void preencherProjeto() {
    System.out.print("Descricao do projeto: ");
    String descricao = sc.nextLine();

    System.out.print("Data de inicio (dd/MM/yyyy): ");
    String dataInicio = sc.nextLine();

    System.out.print("Data do prazo: (dd/MM/yyyy): ");
    String dataPrazo = sc.nextLine();

    System.out.print("Nome do cliente: ");
    String nomeCliente = sc.nextLine();

    int id;

    try {
      id = ecomp.cadastrarProjeto(descricao, dataInicio, dataPrazo, nomeCliente);
    } catch (Exception e) {
      System.out.println("ERRO: " + e.getMessage());
      System.out.println("Tente novamente!");
      iniciarCadastroProjeto();
      return;
    }

    System.out.println("\n== Projeto " + id + " criado! ==\n ");

    System.out.println("1 - Caso deseje inserir contrato");
    System.out.println("2 - Para retornar ao menu");

    int op = Integer.parseInt(sc.nextLine());

    switch (op) {
      case 1:
        associarContrato(id);
        return;
      case 2:
        System.out.println("\nProjeto cadastrado! (sem contrato)");
        telaPrincipal();
        return;
      default:
        return;
    }

  }

  public static void iniciarCadastroProjeto() {
    System.out.println("\n== Cadastrando novo projeto == ");
    preencherProjeto();
    return;
  }

  public static void associarContrato(int id) {
    System.out.println("\n== Associando contrato ao projeto " + id + "==\n");
    System.out.print("Nome do contrato: ");
    String nome = sc.nextLine();
    System.out.print("Caminhos do contrato: ");
    String caminho = sc.nextLine();

    String nomeArquivoPDF;
    try {
      nomeArquivoPDF = ecomp.associarContrato(id, nome, caminho);
    } catch (Exception e) {
      System.out.println("ERRO: " + e.getMessage());
      System.out.println("Tente novamente!");
      associarContrato(id);
      return;
    }
    System.out.println("\nArquivo salvo com sucesso em: " + nomeArquivoPDF);
    System.out.println("Projeto cadastrado!\n");

    
    System.out.println("Digite qualquer tecla pra voltar ao menu");
    sc.nextLine();
    telaPrincipal();
  }


  public static void mostrarProjetosParaSelecao() {
    String projs = ecomp.getProjetosParaSelecao();

    System.out.println("\n\n== Seleção de projeto ==\n");
    System.out.println(projs);

    System.out.print("\nDigite o id do projeto (-1 para voltar ao menu): "); 

    int op = Integer.parseInt(sc.nextLine());

    switch (op) {
      case -1:
        telaPrincipal();
        return;
      default:
        selecionarProjeto(op);
        return;
    }

  }

  public static void selecionarProjeto(int id) {
    if (!ecomp.projetoExiste(id)) {
      System.out.println("ERRO: O projeto id " + id + "nao existe! Tente novamente");
      mostrarProjetosParaSelecao();
      return;
    }

    System.out.println("== Projeto " + id + " selecionado ==");

    System.out.println("1 - para cadastrar etapa do projeto");
    System.out.println("2 - para adicionar desenvolvedor ao projeto");
    System.out.println("-1 para voltar ao menu");

    int op = Integer.parseInt(sc.nextLine());

    switch (op) {
      case -1:
        telaPrincipal();
        return;
      case 1:
        cadastrarEtapa(id);
        return;
    }


  }

  public static void cadastrarEtapa(int id) {
    System.out.println("== Cadastrando etapa ==");

    System.out.print("Cronagrama: ");
    String cronograma = sc.nextLine();
    System.out.print("Status: ");
    String status = sc.nextLine();

    try {
      ecomp.cadastrarEtapaProjeto(id, cronograma, status);
    } catch (Exception e) {
      System.out.println("ERRO: " + e.getMessage());
      System.out.println("Tente novamente!");
      cadastrarEtapa(id);
      return;
    }

    System.out.println("Etapa adicionada com sucesso!\n");

    System.out.println("Digite qualquer tecla pra voltar ao menu");
    sc.nextLine();
    telaPrincipal();

  }
}

