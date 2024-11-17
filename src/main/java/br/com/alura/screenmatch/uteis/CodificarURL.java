package br.com.alura.screenmatch.uteis;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CodificarURL {
    public static String codificarParaURL(String texto) {
        try {
            return URLEncoder.encode(texto, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao codificar para URL: ", e);
        }
    }
}
