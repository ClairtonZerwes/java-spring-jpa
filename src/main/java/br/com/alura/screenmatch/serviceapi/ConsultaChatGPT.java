package br.com.alura.screenmatch.serviceapi;

import br.com.alura.screenmatch.constantes.Constantes;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService(Constantes.CODIGO_API_KEY_CHATGPT);

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("Traduza para o português Brasil o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        try {
            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText();
        } catch (OpenAiHttpException e) {
            if (e.getMessage().contains("quota")) {
                System.out.println("Cota de consultas gratuitas ao OpenAI ChatGPT foi excedida.");
            } else {
                System.out.println("Erro ao fazer a requsição: " + e.getMessage());
            }
            return "Erro ao processar a tradução ChatGPT.";
        }
    }
}
