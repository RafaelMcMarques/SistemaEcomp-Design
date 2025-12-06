package model;

public class Desenvolvedor extends Ecomper {
  public static Desenvolvedor fromSuper(Ecomper ecomper) {
    Desenvolvedor dev = new Desenvolvedor();
    dev.setCargo(ecomper.getCargo());
    dev.setNome(ecomper.getNome()); 
    dev.setEmail(ecomper.getEmail());
    dev.setCpf(ecomper.getCpf());
    dev.setId(ecomper.getId());
    return dev;
  }
}