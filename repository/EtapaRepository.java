package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Etapa;

public class EtapaRepository extends ObjectRepository<Etapa> {


    public EtapaRepository(String path) {
        super(path);
    }

    @Override
    public List<Etapa> getAll() {
        List<Etapa> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                Etapa e = objectFromCSV(line);    
                lista.add(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public Etapa getById(int id) {
        List<Etapa> lista = getAll();
        for (Etapa e : lista) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Etapa insertNew(Etapa etapa) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            etapa.setId(getNextId());
            bw.write(objectToCSV(etapa));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return etapa;
    }

    @Override
    public void update(Etapa obj) {
        List<Etapa> lista = getAll();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == obj.getId()) {
                lista.set(i, obj);
                saveAll(lista);
                return;
            }
        }

        // se não encontrou, insere
        insertNew(obj);
    }

    protected void saveAll(List<Etapa> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            for (Etapa e : lista) {
                bw.write(objectToCSV(e));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String objectToCSV(Etapa e) {
        return e.getId() + ";" + e.getCronograma()+ ";" + e.getStatus();
    }

    public Etapa objectFromCSV(String csv) {
        if (csv == null || csv.isEmpty()) return null;

        String[] parts = csv.split(";", -1);

        Etapa e = new Etapa();

        e.setId(Integer.parseInt(parts[0]));
        e.setCronograma(parts[1]);
        e.setStatus(parts[2]);

        return e;
    }
}
 

