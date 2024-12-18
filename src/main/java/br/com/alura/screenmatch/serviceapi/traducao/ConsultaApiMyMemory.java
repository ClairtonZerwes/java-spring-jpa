package br.com.alura.screenmatch.serviceapi.traducao;

import br.com.alura.screenmatch.constantes.Constantes;
import br.com.alura.screenmatch.serviceapi.ConsumirDadosApi;
import br.com.alura.screenmatch.uteis.CodificarURL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

public class ConsultaApiMyMemory {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ConsumirDadosApi consumirDadosApi = new ConsumirDadosApi();

    public static String obterTraducao(String texto, String linguaOrigemLinguaDestino) {
        String urlConsulta = construirUrlConsulta(texto, linguaOrigemLinguaDestino);
        String json = consumirDadosApi.obterDados(urlConsulta);
        System.out.println("JSON PARA TRADUCAO -> " + json);

        return extrairTextoTraduzido(json);
    }

    private static String construirUrlConsulta(String texto, String linguaOrigemLinguaDestino) {
        return Constantes.ENDERECO_API_MYMEMORY
                + CodificarURL.codificarStringParaURL(texto)
                + "&langpair="
                + CodificarURL.codificarStringParaURL(linguaOrigemLinguaDestino);
    }

    private static String extrairTextoTraduzido(String json) {
        try {
            DadosRespostaTraducao dadosRespostaTraducao = objectMapper.readValue(json, DadosRespostaTraducao.class);
            System.out.println("dadosREspostaTraducao ->" + dadosRespostaTraducao);
            System.out.println("Texto traduzido -> " + dadosRespostaTraducao.textoTraduzido());
            return dadosRespostaTraducao.textoTraduzido();
        } catch (JsonProcessingException e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
