package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.constantes.Constantes;
import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.serviceapi.ConsumirDadosApi;
import br.com.alura.screenmatch.serviceapi.ConverterDados;
import br.com.alura.screenmatch.uteis.CodificarURL;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class Principal {
    private Scanner ler = new Scanner(System.in);
    private ConsumirDadosApi consumirDadosApi = new ConsumirDadosApi();
    private ConverterDados converterDados = new ConverterDados();
    private List<DadosSerie> dadosSerie = new ArrayList<>();
    private static DateTimeFormatter formatoDataIMDB = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter formatarDataBR = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR"));
    //@Autowired
    private SerieRepository serieRepository;

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @PreDestroy
    public void closeResources() {
        serieRepository.flush();
        ler.close();
    }

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
        var opcaoMenu = -1;

        while (opcaoMenu != 0) {
            try {
                var menu = """
                        \n=====  Menu  Principal  =====
                            1 - Buscar Séries
                            2 - Buscar Episódios
                            3 - Listar Series
                            0 - Sair
                        =============================
                        """;
                System.out.print(menu);
                opcaoMenu = ler.nextInt();
                ler.nextLine();

                switch (opcaoMenu) {
                    case 1:
                        buscarSerieImdbApi();
                        break;
                    case 2:
                        buscarEpisodioPorSerie();
                        break;
                    case 3:
                        listarSeriesConsultadas();
                        break;
                    case 0:
                        System.out.println("\nFinalizando Programa...");
                        closeResources();
                        break;
                    default:
                        mensagemPadraoErroOpcaoMenu();
                        throw new IllegalStateException("Opção Menu Digitada = " + opcaoMenu);
                }
            } catch (NumberFormatException | InputMismatchException e) {
                mensagemPadraoErroOpcaoMenu();
            }
        }
    }

    private void listarSeriesConsultadas() {
        /*List<Serie> series = new ArrayList<>();
        series = dadosSerie.stream()
                .map(d -> new Serie(d))
                .collect(Collectors.toList());

        System.out.println("\n-----------------------------------");
        System.out.println("|  Listando  Séries  Consultadas  |");
        System.out.println("-----------------------------------");
        //dadosSerie.forEach(System.out::println);
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
        System.out.println("\nPressione ENTER para voltar ao menu.");
        ler.nextLine();
        */
        System.out.println("\n-------------------------------------");
        System.out.println("| Listando Séries do Banco de Dados |");
        System.out.println("-------------------------------------");

        List<Serie> seriesBD = serieRepository.findAll();
        seriesBD.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
        System.out.println("\nPressione ENTER para voltar ao menu.");
        ler.nextLine();
    }

    private void buscarSerieImdbApi() {
        DadosSerie dadosConsulta = getDadosSerie();
        //dadosSerie.add(dadosConsulta);
        //Interface...SerieRepository serieRepository = new SerieRepository(); {...}
        try {
            dadosSerie.add(dadosConsulta);
            Serie serie = new Serie(dadosConsulta);
            //serieRepository.save(new Serie(dadosConsulta));
            serieRepository.save(serie);
            //System.out.println("\nLista Dados da Série -> \n" + dadosSerie);
            System.out.println("\nBD - Dados da Série -> \n" + serie);
        } catch (Exception e) {
            System.out.println("Exception Dados da Série -> " + e.getMessage());
        }
    }

    private DadosSerie getDadosSerie() {
        var nomeSerieEncoded = CodificarURL.codificarStringParaURL(getDadosSeriesValidados());
        var json = consumirDadosApi.obterDados(Constantes.ENDERECO_API + nomeSerieEncoded + "&apikey=" + Constantes.CODIGO_API_KEY_IMDB);
        System.out.println(json);
        DadosSerie dadosSerie = converterDados.obterDados(json, DadosSerie.class);
        return dadosSerie;
    }

    private String getDadosSeriesValidados() {
        System.out.println("\nInforme o nome da série para pesquisa: ");
        var nomeSerie = ler.nextLine();
        if (nomeSerie.isBlank()) {
            System.out.println("\nO nome da série não pode ser vazio.");
            return getDadosSeriesValidados();
        }

        return nomeSerie;
    }

    private void buscarEpisodioPorSerie() {
        DadosSerie dadosSerie = getDadosSerie();
        System.out.println("\nManipulando  Dados das Temporadas");
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumirDadosApi.obterDados(Constantes.ENDERECO_API + CodificarURL.codificarStringParaURL(dadosSerie.titulo()) + "&season=" + i + "&apikey=" + Constantes.CODIGO_API_KEY_IMDB);
            DadosTemporada dadosTemporada = converterDados.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

    }

    private void mensagemPadraoErroOpcaoMenu() {
        System.out.println("Você deve informar somente números, conforme opções do Menu. Pressione ENTER para continuar...\n");
        ler.nextLine();
    }
}
