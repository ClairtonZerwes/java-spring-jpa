package br.com.alura.screenmatch.constantes;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;

public class Constantes {

    static Dotenv dotenv = Dotenv.load();
    public static final String CODIGO_API_KEY_IMDB = dotenv.get("API_KEY_IMDB"); // criar .env
    public static final String ENDERECO_API = "https://www.omdbapi.com/?t=";
}
