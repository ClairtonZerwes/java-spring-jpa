package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.serviceapi.ConsultaChatGPT;
import br.com.alura.screenmatch.serviceapi.ConsultaGoogleGemini;
import br.com.alura.screenmatch.serviceapi.traducao.ConsultaApiMyMemory;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atoresPrincipais;
    private String posterUrl;
    private String sinopse;
    // @Trasiente executa sem aparecer mensagem de erro no acesso do java jpa ao banco por não ter
    // o relacionamento da classe episodios/serie criando pk-fk entre a tabelas/classe na modelagem de dados
    @Transient
    private List<Episodio> episodios = new ArrayList<>();

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0);
        //this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim()).orElse("Desconhecido").toString();
        try {
            this.genero = Categoria.valueOf(String.valueOf(Categoria.fromString(dadosSerie.genero().split(",")[0].trim())));
        } catch (IllegalArgumentException e) {
            this.genero = Categoria.valueOf("Não Informado.");
        }
        this.atoresPrincipais = dadosSerie.atoresPrincipais();
        this.posterUrl = dadosSerie.posterUrl();
        try {
            var sinopseTraduzida = ConsultaGoogleGemini.obterTraducao(dadosSerie.sinopse());
            /*
            var sinopseTraduzida = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse());

            if (sinopseTraduzida.contains("Erro ao processar a tradução")) {
                // Criar ENUM para langpair de idioma origem para destino para tradução
                ConsultaApiMyMemory.obterTraducao(dadosSerie.sinopse(), "en|pt-br").trim();
            }
            */
            this.sinopse = sinopseTraduzida;
        } catch (Exception e) {
            this.sinopse = "Erro ao processar a tradução";
            System.out.println("Erro ao processar a tradução: " + e.getMessage());
        }
    }

    public Serie() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtoresPrincipais() {
        return atoresPrincipais;
    }

    public void setAtoresPrincipais(String atoresPrincipais) {
        this.atoresPrincipais = atoresPrincipais;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return "\nDados da Série -> " + titulo +
                "\nTotal de Temporadas = " + totalTemporadas +
                ", Avaliação = " + avaliacao +
                ", Genêro = " + genero +
                ", Atores Principais = " + atoresPrincipais +
                ", URL do Poster = " + posterUrl +
                ", Resumo Sinopse = " + sinopse
                ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
