package br.com.alura.screenmatch.serviceapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class GenericDeserializer extends JsonDeserializer<Number>{
    @Override
    public Number deserialize(JsonParser dadoJson, DeserializationContext tipoDesserializacao) throws IOException
    {
        String dados = dadoJson.getText();
        if (dados.equalsIgnoreCase("N/A")) {
            return 0; // Retorna 0 para int ou double
        } else {
            try {
                return Integer.parseInt(dados); // Tenta converter para int primeiro
            } catch (NumberFormatException e) {
                return Double.parseDouble(dados); // Se não for int, tenta converter para double
            }
        }
    }

    private Number converterParaNumero(String dadosTexto) {
        // Lógica de conversão personalizada quando desserializar direto em string e depois converter
        if (dadosTexto.equalsIgnoreCase("N/A")) {
            return 0;
        } else {
            try {
                return Integer.parseInt(dadosTexto);
            } catch (NumberFormatException e) {
                return Double.parseDouble(dadosTexto);
            }
        }
    }
}
