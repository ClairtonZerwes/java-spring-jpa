package br.com.alura.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Double avaliacao;
    private LocalDate dataLancamento;
    private Integer duracao;
    private String enredo;

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        try {
            this.temporada = numeroTemporada;
        } catch (NumberFormatException ex) {
            this.temporada = 0;
        }
        try {
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        } catch (DateTimeException ex) {
            this.dataLancamento = null;
        }

        this.duracao = dadosEpisodio.duracao();
        this.enredo = dadosEpisodio.enredo();
        this.titulo = dadosEpisodio.titulo();
    }

    @Override
    public String toString() {
        return "Episodio{" +
                "Temporada: " + temporada +
                ", Titulo: " + titulo +
                ", Avaliação: " + avaliacao +
                ", Data Lançamento: " + dataLancamento +
                ", Duracao: " + duracao + "(minutos)" +
                ", Enredo: '" + enredo + '\'' + '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer duracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        duracao = duracao;
    }

    public String getEnredo() {
        return enredo;
    }

    public void setEnredo(String enredo) {
        this.enredo = enredo;
    }
}