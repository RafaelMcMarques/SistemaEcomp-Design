package model;

public class Etapa {

    private int id = -1;
    private String cronograma;
    private String status;

    public Etapa() {
        // construtor padrão
    }

    public Etapa(String cronograma, String status) {
        this.setCronograma(cronograma);
        this.setStatus(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCronograma() {
        return cronograma;
    }

    public void setCronograma(String cronograma) {
        if (cronograma == null || cronograma.isBlank()) {
            throw new IllegalArgumentException("Cronograma da etapa não pode ser vazio.");
        }
        this.cronograma = cronograma;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status da etapa não pode ser vazio.");
        }
        this.status = status;
    }

}
