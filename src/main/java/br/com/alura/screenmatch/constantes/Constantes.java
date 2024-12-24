package br.com.alura.screenmatch.constantes;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;

public class Constantes {

    private static Dotenv dotenv = Dotenv.load();
    public static final String CODIGO_API_KEY_IMDB = dotenv.get("API_KEY_IMDB");
    public static final String ENDERECO_API = "https://www.omdbapi.com/?t=";
    public static final String CODIGO_API_KEY_CHATGPT = dotenv.get("API_KEY_CHATGPT");
    public static final String ENDERECO_API_MYMEMORY = "https://api.mymemory.translated.net/get?q=";
    /*
    public static  final String DB_URL = dotenv.get("DB_URL");
    public static  final String DB_USERNAME = dotenv.get("DB_USERNAME");
    public static  final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
     */
    public static  final String CODIGO_API_KEY_GOOGLEGEMINI = dotenv.get("API_KEY_GOOGLEGEMINI");
    public static  final String MODEl_NAME_API_GOOGLEGEMINI = "gemini-1.5-flash";
}
