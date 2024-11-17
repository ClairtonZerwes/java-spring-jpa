package br.com.alura.screenmatch;

import br.com.alura.screenmatch.constantes.Constantes;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.serviceapi.ConsumirDadosApi;
import br.com.alura.screenmatch.serviceapi.ConverterDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        var consumirDadosApi = new ConsumirDadosApi();
		var urlConsultaApi = "https://www.omdbapi.com/?t=gilmore+girls&apikey=" + Constantes.CODIGO_API_KEY_IMDB;
		/*
		var json = consumirDadosApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
		*/

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
		/*
		DadosEpisodio dadosEpisodio = converterDados.obterDados(json, DadosEpisodio.class);
		System.out.println("Dados do episodio convertido: \n" + dadosEpisodio);
		*/

		System.out.println("\nManipulando  Dados das TEMPORADAS");
		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<=dadosSerie.totalTemporadas(); i++) {
			json = consumirDadosApi.obterDados(Constantes.ENDERECO_API + "gilmore+girls&season=" + i + "&apikey=" + Constantes.CODIGO_API_KEY_IMDB);
			DadosTemporada dadosTemporada = converterDados.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}
}
