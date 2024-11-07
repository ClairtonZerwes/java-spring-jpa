package br.com.alura.screenmatch;

import br.com.alura.model.DadosSerie;
import br.com.alura.serviceapi.ConsumirDadosApi;
import br.com.alura.serviceapi.ConverterDados;
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
        var consumirDadosApi = new ConsumirDadosApi();
		final String codigoApiKey = "2d011eff";
		var urlConsultaApi = "https://www.omdbapi.com/?t=gilmore+girls&apikey=" + codigoApiKey;
/*		var json = consumirDadosApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);*/

		System.out.println("URL MONTADA = " + urlConsultaApi);
		var json = consumirDadosApi.obterDados(urlConsultaApi);
		System.out.println(json);

		ConverterDados converterDados = new ConverterDados();
		DadosSerie dadosSerie = converterDados.obterDados(json, DadosSerie.class);
		System.out.println("Dados convertidos \n" + dadosSerie);
	}
}
