package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Projeto;
import model.Cliente;
import model.Etapa;

public class ProjetoRepository extends ObjectRepository<Projeto> {

    private EtapaRepository etapaRepository;
    
    public ProjetoRepository(String path) {
        super(path);
        this.etapaRepository = new EtapaRepository("data/etapa.csv");
    }


    @Override
    public List<Projeto> getAll() {
        List<Projeto> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    Projeto novProjeto = objectFromCSV(linha);
                    lista.add(novProjeto);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public Projeto getById(int id) {
        for (Projeto p : getAll()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Projeto insertNew(Projeto projeto) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            projeto.setId(getNextId());
            if (projeto.getEtapas() != null) {
                for (Etapa etapa : projeto.getEtapas()) {
                    if (etapa.getId() == -1)
                        etapaRepository.insertNew(etapa);
                }
            }
            bw.write(objectToCSV(projeto));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return projeto;
    }

    @Override
    public void update(Projeto projeto) {
        List<Projeto> todos = getAll();
        List<Projeto> novos = new ArrayList<>();

        for (Projeto p : todos) {
            if (p.getId() == projeto.getId()) {
                if (projeto.getEtapas() != null) {
                    for (Etapa etapa : projeto.getEtapas()) {
                        if (etapa.getId() == -1)
                            etapaRepository.insertNew(etapa);
                    }
                }
                novos.add(projeto);
            } else {
                novos.add(p);
            }
        }

        saveAll(novos);
    }

    private void saveAll(List<Projeto> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            for (Projeto p : lista) {
                bw.write(objectToCSV(p));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Projeto objectFromCSV(String csv) {
      if (csv == null || csv.isEmpty()) return null;

      String[] parts = csv.split(";", -1); 

      Projeto p = new Projeto();

      int id = parts.length > 0 ? Integer.valueOf(parts[0]) : -1;
      p.setId(id);


      // TODO: buscar cliente corretamente
      String nomeCliente = parts[1];
      Cliente cliente = new Cliente();
      cliente.setNome(nomeCliente);
      p.setCliente(cliente);

      List<Etapa> etapas = new ArrayList<>();
      if (!parts[2].isEmpty()) {
          String[] etapaTokens = parts[2].split(",");
          for (String t : etapaTokens) {
              try {
                  int etapaId = Integer.parseInt(t.trim());
                  Etapa etapa = etapaRepository.getById(etapaId); 
                  etapas.add(etapa);
              } catch (NumberFormatException ignored) {}
          }
      }
      p.setEtapas(etapas);

      p.setDataInicio(parts.length > 3 ? parts[3] : "");

      p.setDataPrazo(parts.length > 4 ? parts[4] : "");

      p.setDescricao(parts.length > 5 ? parts[5] : "");

      if (parts.length > 7 && parts[6] != "" && parts[7] != "") {
        p.setNomeContrato(parts[6]);

        p.setCaminhosContrato(parts[7]); 

      }
      
      return p;
   }

    public String objectToCSV(Projeto p) {
      StringBuilder sb = new StringBuilder();

      sb.append(p.getId()).append(";");

      // etapasIDs separados por vírgula
      String etapasStr = "sem etapas";
      List<Etapa> etapas = p.getEtapas();
      if (etapas != null && !etapas.isEmpty()) {
        etapasStr = "";
          for (int i = 0; i < etapas.size(); i++) {
              etapasStr += etapas.get(i).getId();
              if (i < etapas.size() - 1) {
                  etapasStr += ",";
              }
          }
      }

      sb.append(p.getCliente() != null ? p.getCliente().getNome() : "Sem cliente").append(";")
      .append(etapasStr).append(";")
      .append(p.getDataInicio() != null ? p.getDataInicio() : "").append(";")
      .append(p.getDataPrazo() != null ? p.getDataPrazo() : "").append(";")
      .append(p.getDescricao() != null ? p.getDescricao() : "").append(";")
      .append(p.getNomeContrato() != null ? p.getNomeContrato() : "").append(";")
      .append(p.getCaminhosContrato() != null ? p.getCaminhosContrato() : "");

      return sb.toString();
    }
}
