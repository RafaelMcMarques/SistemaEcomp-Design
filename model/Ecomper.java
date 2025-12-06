package model;

public class Ecomper {


    private String nome;
    private int id;
    private String cpf;
    private String cargo;
    private String email;

    public Ecomper() { }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validar(nome, "nome");
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        validar(cpf, "cpf");
        if (!cpf.matches("\\d+")) {
          throw new IllegalArgumentException("cpf deve conter apenas números.");
        }
        this.cpf = cpf;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        validar(cargo, "cargo");
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validar(email, "email");
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Email inválido: precisa conter @ e .");
        }
        this.email = email;
    }

    private void validar(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O campo '" + campo + "' não pode ser vazio.");
        }
    }

    @Override
    public String toString() {
        return "=== Ecomper " + id + " ===\n" +
              "Nome: " + nome + "\n" +
              "CPF: " + cpf + "\n" +
              "Cargo: " + cargo + "\n" +
              "Email: " + email + "\n"; 
    }

}
