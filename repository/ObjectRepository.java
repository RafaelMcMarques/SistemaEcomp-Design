package repository;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public abstract class ObjectRepository<T> {
    protected String path;

    public ObjectRepository(String path) {
        this.path = path;
    }

    // id eh sempre a primeira coluna da tabela, retornar o proximo maior 
    protected final int getNextId() {
        int maior = -1;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;

                int idx = line.indexOf(';');
                String numeroStr;

                if (idx == -1)
                    numeroStr = line;         // linha sem ponto e vírgula → a linha inteira é o ID
                else
                    numeroStr = line.substring(0, idx); // primeira coluna

                int valor = Integer.parseInt(numeroStr);

                if (valor > maior)
                    maior = valor;
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler IDs do arquivo: " + path, e);
        }

        return maior + 1;
    }

    // Retorna todos os objetos do arquivo
    public abstract List<T> getAll();

    // Busca um objeto pelo ID (assume que T tem getId())
    public abstract T getById(int id);

    // Atualiza um objeto existente
    public abstract void update(T obj);

    // Insere um novo objeto
    public abstract T insertNew(T obj);

    public abstract T objectFromCSV(String csv);

    public abstract String objectToCSV(T obj);

}
