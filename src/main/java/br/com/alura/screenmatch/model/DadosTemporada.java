package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(//@JsonAlias("Season") @JsonDeserialize(using = GenericDeserializer.class) Integer numeroDaTemporada,
                             @JsonAlias("Season") Integer numeroDaTemporada,
                             @JsonAlias("Year") String anoDeLancamento,
                             @JsonAlias("Episodes") List<DadosEpisodio> episodios) {
}
