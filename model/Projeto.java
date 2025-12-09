package model;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Projeto {

    private Cliente cliente;
    private List<Etapa> etapas;
    private List<Desenvolvedor> desenvolvedores;
    private String dataInicio;
    private String dataPrazo;
    private String descricao;
    private String nomeContrato;
    private String caminhosContrato;
    private int id = -1;

    // Construtor padrão
    public Projeto() {
    }

    // Getters e Setters

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<Etapa> etapas) {
        this.etapas = etapas;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public List<Desenvolvedor> getDesenvolvedores() {
        return desenvolvedores;
    }

    public void setDesenvolvedores(List<Desenvolvedor> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }

    public void setDataInicio(String dataInicio) {
        if (dataInicio == null || dataInicio.isBlank()) {
            throw new IllegalArgumentException("Data de início não pode ser vazia.");
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate.parse(dataInicio, fmt);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data do início inválida. Use o formato dd/MM/yyyy.");
        }
        this.dataInicio = dataInicio;
    }

    public String getDataPrazo() {
        return dataPrazo;
    }

    public void setDataPrazo(String dataPrazo) {

        if (dataPrazo == null || dataPrazo.isBlank()) {
            throw new IllegalArgumentException("Data do prazo não pode ser vazia.");
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate prazo;
        try {
            prazo = LocalDate.parse(dataPrazo, fmt);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data do prazo inválida. Use o formato dd/MM/yyyy.");
        }

        // validar contra dataInicio (que já deve estar definida)
        if (this.dataInicio != null && dataInicio != "") {
            LocalDate inicio = LocalDate.parse(this.dataInicio, fmt);

            if (prazo.isBefore(inicio)) {
                throw new IllegalArgumentException("O prazo não pode ser anterior à data de início.");
            }
        }

        this.dataPrazo = dataPrazo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        }
        this.descricao = descricao;
    }

    public String getNomeContrato() {
        return nomeContrato;
    }

    public void setNomeContrato(String nomeContrato) {
        if (nomeContrato == null || nomeContrato.isBlank()) {
            throw new IllegalArgumentException("Nome do contrato não pode ser vazio.");
        }
        this.nomeContrato = nomeContrato;
    }

    public String getCaminhosContrato() {
        return caminhosContrato;
    }

    public void setCaminhosContrato(String caminhosContrato) {
        if (caminhosContrato == null || caminhosContrato.isBlank()) {
            throw new IllegalArgumentException("Caminho do contrato não pode ser vazio.");
        }
        this.caminhosContrato = caminhosContrato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addEtapa(Etapa e) {
      this.etapas.add(e);
    }

    public void addDesenvolvedor(Desenvolvedor d) {
        this.desenvolvedores.add(d);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("-- Projeto ").append(id >= 0 ? id : "(sem ID)").append("-- \n\n");

        sb.append("Nome do cliente: ")
        .append(cliente != null ? cliente.getNome() : "(Sem cliente)").append("\n");

        sb.append("Descrição: ")
        .append(descricao != null ? descricao : "(vazio)").append("\n");

        sb.append("Data de Início: ")
        .append(dataInicio != null ? dataInicio : "(não definida)").append("\n");

        sb.append("Data de Prazo: ")
        .append(dataPrazo != null ? dataPrazo : "(não definida)").append("\n");

        sb.append("Nome do Contrato: ")
        .append(nomeContrato != null && nomeContrato != "" ? nomeContrato : "(vazio)").append("\n");

        sb.append("Caminho do Contrato: ")
        .append(caminhosContrato != null && caminhosContrato != "" ? caminhosContrato : "(vazio)").append("\n");

        if (etapas != null && !etapas.isEmpty()) {
            sb.append("Etapas: \n");
            for (Etapa e : etapas) {
                sb.append("  Cronograma: ").append(e.getCronograma()).append(" | ");
                sb.append("Status: ").append(e.getStatus()).append("\n");
            }
        } else {
            sb.append("Etapas: (vazio)\n");
        }

        if (desenvolvedores != null && !desenvolvedores.isEmpty()) {
            sb.append("Desenvolvedores: \n");
            for (Desenvolvedor d : desenvolvedores) {
                sb.append("  Nome: ").append(d.getNome()).append(" | ");
                sb.append("Cargo: ").append(d.getCargo()).append("\n");
            }

        } else {
            sb.append("Desenvolvedores: (vazio)\n");
        }

        return sb.toString();
    }

}