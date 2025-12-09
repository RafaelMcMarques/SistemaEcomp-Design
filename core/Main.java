package core;

import java.util.*;

public class Main {
  private static Scanner sc = new Scanner(System.in);
  private static Ecomp ecomp = new Ecomp();
  
  public static void main(String[] args) {

    telaPrincipal();

  }

  public static void telaPrincipal() {
    System.out.println("\n\n==== MENU SISTEMA ECOMP ====\n");
    System.out.println("1 - para exibir relatorio de projetos");
    System.out.println("2 - para cadastrar projeto");
    System.out.println("3 - para mostrar projetos para seleção");
    System.out.println("4 - para listar ecompers");
    System.out.println("5 - para cadastrar ecomper");

    System.out.println("0 - para sair");

    System.out.print("\nDigite uma opção: ");
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
      case 4:
        listarEcompers();
        return;
      case 5:
        iniciarCadastroMembro();
        return;
      case 0:
        return;
      default:
        return;
    }
  }


  public static void exibirRelatorioProjetos() {
    System.out.println("\n\n== RELATORIO DE PROJETOS ==\n");
    System.out.print(ecomp.getRelatorioProjetos());
    System.out.println("== FIM DO RELATORIO DE PROJETOS ==\n");

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
      System.out.println("\nERRO: " + e.getMessage());
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
    System.out.println("\n\n== Cadastrando novo projeto == \n");
    preencherProjeto();
    return;
  }

  public static void associarContrato(int id) {
    System.out.println("\n== Associando contrato ao projeto " + id + " ==\n");
    System.out.print("Nome do contrato: ");
    String nome = sc.nextLine();
    System.out.print("Caminho do contrato: ");
    String caminho = sc.nextLine();

    String nomeArquivoPDF;
    try {
      nomeArquivoPDF = ecomp.associarContrato(id, nome, caminho);
    } catch (Exception e) {
      System.out.println("\nERRO: " + e.getMessage());
      System.out.println("Tente novamente!");
      associarContrato(id);
      return;
    }
    System.out.println("\nArquivo salvo com sucesso em: " + nomeArquivoPDF);
    System.out.println("\n== Projeto cadastrado! ==\n");

    
    System.out.println("Digite qualquer tecla pra voltar ao menu");
    sc.nextLine();
    telaPrincipal();
  }


  public static void mostrarProjetosParaSelecao() {
    String projs = ecomp.getProjetosParaSelecao();

    System.out.println("\n\n== Seleção de projeto ==\n");
    System.out.println(projs);

    System.out.print("Digite o id do projeto (-1 para voltar ao menu): "); 

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
      System.out.println("\nERRO: O projeto id " + id + " não existe! Tente novamente");
      mostrarProjetosParaSelecao();
      return;
    }

    System.out.println("\n\n== Projeto " + id + " selecionado ==\n");

    System.out.println("1 - para cadastrar etapa do projeto");
    System.out.println("2 - para adicionar desenvolvedor ao projeto");
    System.out.print("\nDigite uma opção (-1 para voltar ao menu): ");

    int op = Integer.parseInt(sc.nextLine());

    switch (op) {
      case -1:
        telaPrincipal();
        return;
      case 1:
        cadastrarEtapa(id);
        return;
      case 2:
        adicionarDesenvolvedor(id);
        return;
    }


  }

  public static void cadastrarEtapa(int id) {
    System.out.println("\n\n== Cadastrando etapa ao projeto " + id +  " ==\n");

    System.out.print("Cronograma: ");
    String cronograma = sc.nextLine();
    System.out.print("Status: ");
    String status = sc.nextLine();

    try {
      ecomp.cadastrarEtapaProjeto(id, cronograma, status);
    } catch (Exception e) {
      System.out.println("\nERRO: " + e.getMessage());
      System.out.println("Tente novamente!");
      cadastrarEtapa(id);
      return;
    }

    System.out.println("\nEtapa adicionada com sucesso!\n");

    System.out.println("Digite qualquer tecla pra voltar ao menu");
    sc.nextLine();
    telaPrincipal();

  }

  public static void iniciarCadastroMembro() {
    System.out.println("\n\n== Cadastrando membro ==\n");

    preencherEcomper();
    return;
  }

  public static void preencherEcomper() {

    System.out.print("Nome: ");
    String nome = sc.nextLine();

    System.out.print("Email: ");
    String email = sc.nextLine();

    System.out.print("CPF: ");
    String cpf = sc.nextLine();

    System.out.print("Cargo: ");
    String cargo = sc.nextLine();

    int id;

    try {
      id = ecomp.cadastrarMembro(nome, email, cpf, cargo);
    } catch (Exception e) {
      System.out.println("\nERRO: " + e.getMessage());
      System.out.println("Tente novamente!");
      iniciarCadastroMembro();
      return;
    }

    System.out.println("\nEcomper " + id + " cadastrado!");

    System.out.println("\nDigite qualquer tecla pra voltar ao menu");
    sc.nextLine();
    telaPrincipal();

  }

  public static void listarEcompers() {
    String ecompersStr = ecomp.getListaEcompersStr();

    System.out.println("\n\n=== LISTA DE ECOMPERS === \n");

    System.out.print(ecompersStr);

    System.out.println("== FIM DA LISTA DE ECOMPERS ==\n");

    System.out.println("Digite qualquer tecla pra voltar ao menu");
    sc.nextLine();
    telaPrincipal();
  }

  public static void adicionarDesenvolvedor(int projId) {
    System.out.println("\n\n== Vinculando desenvolvedor ao projeto ==\n");

    String devsList = ecomp.getDesenvolvedoresParaSelecao(projId);
    System.out.println(devsList);

    System.out.print("Digite o ID do ecomper (-1 para sair): ");

    int devId = Integer.parseInt(sc.nextLine());
    
    if (devId == -1) {
      telaPrincipal();
      return;
    }

    ecomp.vincularDesenvolvedorProjeto(projId, devId);
    System.out.println("\nDesenvolvedor vinculado com sucesso!\n");

    System.out.println("1 - para vincular outro desenvolvedor");
    System.out.println("2 - para voltar ao menu");


    System.out.print("\nDigite uma opcao: ");
    int op = Integer.parseInt(sc.nextLine());

    switch (op) {
      case 1:
        adicionarDesenvolvedor(projId);
        return;
      case 2:
        telaPrincipal();
        return;
    }

  }

}


