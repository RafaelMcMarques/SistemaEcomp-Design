package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Ecomper;

public class EcomperRepository extends ObjectRepository<Ecomper> {

    public EcomperRepository(String path) {
      super(path);
    }

    @Override
    public List<Ecomper> getAll() {
        List<Ecomper> lista = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                Ecomper e = objectFromCSV(line);
                lista.add(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    // Busca pelo ID
    @Override
    public Ecomper getById(int id) {
        for (Ecomper e : getAll()) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    // Atualiza um objeto existente
    @Override
    public void update(Ecomper obj) {
        List<Ecomper> lista = getAll();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == obj.getId()) {
                lista.set(i, obj);
                saveAll(lista);
                return;
            }
        }

        throw new RuntimeException("Ecomper com id " + obj.getId() + " não encontrado.");
    }

    // Insere um novo objeto (gerando novo ID)
    @Override
    public Ecomper insertNew(Ecomper obj) {
        List<Ecomper> lista = getAll();

        int novoId = gerarNovoId();
        obj.setId(novoId);

        lista.add(obj);
        saveAll(lista);
        return obj;
    }

    // Usa o primeiro campo do CSV para encontrar maior ID
    private int gerarNovoId() {
        int maior = -1;

        File file = new File(path);
        if (!file.exists()) return 1;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] partes = line.split(";");
                int id = Integer.parseInt(partes[0]);

                if (id > maior) maior = id;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return maior + 1;
    }

    // Salva toda a lista no arquivo
    private void saveAll(List<Ecomper> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            for (Ecomper e : lista) {
                bw.write(objectToCSV(e));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


  public String objectToCSV(Ecomper e) {
    StringBuilder sb = new StringBuilder();
    sb.append(e.getId()).append(";")
      .append(e.getNome()).append(";")
      .append(e.getCpf()).append(";")
      .append(e.getCargo()).append(";")
      .append(e.getEmail());
    return sb.toString();
  }

  public Ecomper objectFromCSV(String csv) {
    String[] parts = csv.split(";", -1);

    Ecomper e = new Ecomper();
    e.setId(Integer.parseInt(parts[0]));
    e.setNome(parts[1]);
    e.setCpf(parts[2]);
    e.setCargo(parts[3]);
    e.setEmail(parts[4]);

    return e;
}

}
