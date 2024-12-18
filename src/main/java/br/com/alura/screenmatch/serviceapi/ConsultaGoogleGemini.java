package br.com.alura.screenmatch.serviceapi;

import br.com.alura.screenmatch.constantes.Constantes;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class ConsultaGoogleGemini {

    public static String obterTraducao(String texto) {
        ChatLanguageModel apiGemini = GoogleAiGeminiChatModel.builder()
                .apiKey(Constantes.CODIGO_API_KEY_GOOGLEGEMINI)
                .modelName(Constantes.MODEl_NAME_API_GOOGLEGEMINI)
                .build();

        try {
            return apiGemini.generate("Traduzir para o português Brasil o texto: " + texto);
        } catch (Exception e) {
            System.out.println("Erro ao traduzir o texto com Api do GoogelGemini: " + e.getMessage());
            return "Erro ao processar a tradução GoogleGemini.";
        }
    }
}
