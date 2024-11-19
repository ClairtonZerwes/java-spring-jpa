package br.com.alura.screenmatch;

import br.com.alura.screenmatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
        /*
        var consumirDadosApi = new ConsumirDadosApi();
        var urlConsultaApi = "https://www.omdbapi.com/?t=gilmore+girls&apikey=" + Constantes.CODIGO_API_KEY_IMDB;
		var json = consumirDadosApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);

		System.out.println("URL MONTADA = " + urlConsultaApi);
		var json = consumirDadosApi.obterDados(urlConsultaApi);
		System.out.println(json);

		ConverterDados converterDados = new ConverterDados();
		DadosSerie dadosSerie = converterDados.obterDados(json, DadosSerie.class);
		System.out.println("Dados convertidos \n" + dadosSerie);

		System.out.println("\nManipulando dados de EPISODIOS");
		urlConsultaApi = "https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=" + Constantes.CODIGO_API_KEY_IMDB;
		System.out.println("URL do episodio consutlado: " + urlConsultaApi);
		json = consumirDadosApi.obterDados(urlConsultaApi);
		System.out.println(json + "\n");

		// ERRO AO CONVERTER DADOS VERIFICAR
		DadosEpisodio dadosEpisodio = converterDados.obterDados(json, DadosEpisodio.class);
		System.out.println("Dados do episodio convertido: \n" + dadosEpisodio);
		*/
	}
}
