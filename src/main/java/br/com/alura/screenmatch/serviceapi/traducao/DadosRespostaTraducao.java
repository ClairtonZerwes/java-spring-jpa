package br.com.alura.screenmatch.serviceapi.traducao;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosRespostaTraducao(@JsonAlias("translatedText") String textoTraduzido) {
}
