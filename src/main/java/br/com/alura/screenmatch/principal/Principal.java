package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.constantes.Constantes;
import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.serviceapi.ConsumirDadosApi;
import br.com.alura.screenmatch.serviceapi.ConverterDados;
import br.com.alura.screenmatch.uteis.CodificarURL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Principal {
    private Scanner ler = new Scanner(System.in);
    private ConsumirDadosApi consumirDadosApi = new ConsumirDadosApi();
    private ConverterDados converterDados = new ConverterDados();

    private static DateTimeFormatter formatoDataIMDB = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter formatarDataBR = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR"));

    private static String formatarDataParaExibir(String dataLancamentoEpisodio) {
        LocalDate dataFormatadaIMDB = LocalDate.parse(dataLancamentoEpisodio, formatoDataIMDB);
        return dataFormatadaIMDB.format(formatarDataBR);
    }

    public void exibeMenu() {
        System.out.println("Informe o nome da série para pesquisa: ");
        var nomeSerie = ler.nextLine();
        var nomeSerieEncoded = CodificarURL.codificarParaURL(nomeSerie);
        //System.out.println(Constantes.ENDERECO_API + nomeSerieEncoded + "&apikey=" + Constantes.CODIGO_API_KEY_IMDB);
        var json = consumirDadosApi.obterDados(Constantes.ENDERECO_API + nomeSerieEncoded + "&apikey=" + Constantes.CODIGO_API_KEY_IMDB);
        System.out.println(json);
        DadosSerie dadosSerie = converterDados.obterDados(json, DadosSerie.class);
        System.out.println("\nDados da Serie " + nomeSerie + ": \n" + dadosSerie);

        System.out.println("\nManipulando  Dados das TEMPORADAS");
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i<=dadosSerie.totalTemporadas(); i++) {
            json = consumirDadosApi.obterDados(Constantes.ENDERECO_API + nomeSerieEncoded + "&season=" + i + "&apikey=" + Constantes.CODIGO_API_KEY_IMDB);
            DadosTemporada dadosTemporada = converterDados.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        /*
        for(int i = 0; i < dadosSerie.totalTemporadas(); i++) {
            List<DadosEpisodio> dadosEpisodios = temporadas.get(i).episodios();
            System.out.println("\nTemporada " + (i+1));

            for (int j = 0; j < dadosEpisodios.size(); j++) {
                LocalDate dataFormatadaIMDB = LocalDate.parse(dadosEpisodios.get(j).dataLancamento(), formatoDataIMDB);
                String dataLancamento = dataFormatadaIMDB.format(formatarDataBR);
                System.out.println("Episodio: " + (j+1) + " - " + dadosEpisodios.get(j).titulo() + " - Lançando em " + dataLancamento);
            }
        }*/
        AtomicInteger numeroTemporada = new AtomicInteger(1);
        //final int[] numeroTemporada = {1};
        temporadas.forEach(t -> {
            // System.out.println("\nTemporada " + numeroTemporada[0]);
            System.out.println("\nTemporada " + numeroTemporada.getAndIncrement());
            t.episodios().forEach(e -> {
                System.out.println("Episodio " + (t.episodios().indexOf(e) + 1) + " - " + e.titulo() + " - Lançando em " + formatarDataParaExibir(e.dataLancamento()));
            });
            numeroTemporada.getAndIncrement();
            //numeroTemporada[0]++;
        });
    }
}