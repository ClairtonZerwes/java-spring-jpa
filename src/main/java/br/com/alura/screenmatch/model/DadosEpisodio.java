package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            /*
                            @JsonAlias("Season") @JsonDeserialize(using = GenericDeserializer.class) Integer numeroDaTemporada,
                            @JsonAlias("Episode") @JsonDeserialize(using = GenericDeserializer.class) Integer numeroDoEpisodio,
                            @JsonAlias("imdbRating") @JsonDeserialize(using = GenericDeserializer.class) Double avaliacao,
                            */

                            @JsonAlias("Released") String dataLancamento,
                            @JsonAlias("Runtime") Integer duracao,
                            @JsonAlias("Plot") String enredo) {
}
