package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.constantes.Constantes;
import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.serviceapi.ConsumirDadosApi;
import br.com.alura.screenmatch.serviceapi.ConverterDados;
import br.com.alura.screenmatch.uteis.CodificarURL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Principal {
    private Scanner ler = new Scanner(System.in);
    private ConsumirDadosApi consumirDadosApi = new ConsumirDadosApi();
    private ConverterDados converterDados = new ConverterDados();
    private static DateTimeFormatter formatoDataIMDB = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter formatarDataBR = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR"));

    private static String formatarDataPadraoBrasil(String dataLancamentoEpisodio) {
        LocalDate dataFormatadaIMDB;

        try {
            dataFormatadaIMDB = LocalDate.parse(dataLancamentoEpisodio, formatoDataIMDB);
        } catch (DateTimeParseException e) {
            return null;
        }
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
                System.out.println("Episodio " + (t.episodios().indexOf(e) + 1) + " - " + e.titulo() + " - Lançando em " + formatarDataPadraoBrasil(e.dataLancamento()));
            });
            numeroTemporada.getAndIncrement();
            //numeroTemporada[0]++;
        });

        List<DadosEpisodio> listaDadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\n*** Top 5 Episodios ***");
        listaDadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(e -> new Episodio(t.numeroDaTemporada(), e))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        // Outro Exemplo manipulando Datas
        DateTimeFormatter formatoDataBR = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //formatarDataPadraoBrasil()
        LocalDate data = LocalDate.now();
        System.out.println("Data padrão BR (" + data + "): " + data.format(formatoDataBR));

        Scanner leitura = new Scanner(System.in);
        System.out.println("\nDigite um parte do título do episódio para pesquisar");
        var parteDoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                        .filter(e -> e.getTitulo().toUpperCase().contains(parteDoTitulo.toUpperCase()))
                        .findFirst();

        if (episodioBuscado.isPresent()) {
            System.out.println("Episódio Encontrado!!!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
            System.out.println("Titulo: " + episodioBuscado.get().getTitulo());
        } else {
            System.out.println("Episódio não Encontrado.");
        }


        System.out.println("\nInforme a partir de qual ano para pesquisar os episodios.");
        var ano = leitura.nextInt();
        leitura.nextLine();
        LocalDate dataBusca = LocalDate.of(ano, 1,1);

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                        " Episódio: " + e.getTitulo() +
                        " Data Lançamento: " + e.getDataLancamento().format(formatoDataBR)
                ));

        // Obtendo Média da Avaliação por Temporada
        System.out.println("\nImprimindo Média da Temporada - Por Notas das Avaliações!");
        Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacaoPorTemporada);

        // DoubleSummaryStatistics
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("\nTrabalhando com DoubleSummaryStatistics (Todas Informações): " + est);
        System.out.println("Quantidade: " + est.getCount());
        System.out.println("Média Geral: " + est.getAverage());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Melhor episódio: " + est.getMax());

        // Principal objetivo da função peek() e auxiliar em um Debug visual se as outras funções do stream()
        // Estão de acordando com a lógica e filtrando e executando as operações como era esperado.
        /*
        System.out.println("\n*** Verificando etapas do filtro de dados com PEEK()[Olhadinha] ***\n *** Top 10 Episodios ***");
        listaDadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro Filtro N/A " + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação " + e))
                .limit(10)
                .peek(e -> System.out.println("Limite " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento " + e ))
                .forEach(System.out::println);
         */
    }
}
