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
    private List<Serie> seriesBD = new ArrayList<>();

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
                        \n==================  Menu  Principal  ==================
                            1 - Buscar Séries
                            2 - Buscar Episódios
                            3 - Listar Series
                            4 - Buscar Série por Título
                            5 - Buscar Série por Ator
                            6 - Listar Top 5 Séries
                            7 - Buscar Séries por Categoria
                            8 - Buscar Séries/Temporadas - Avaliação Mínima
                            0 - Sair
                        =======================================================
                        Informe uma das opções acima [0 à 8]: 
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
                    case 4:
                        buscarSeriePorTituloParcial();
                        break;
                    case 5:
                        buscarSeriesPorAtor();
                        break;
                    case 6:
                        buscarTop5SeriesPorOrdemDeAvaliacaoDesc();
                        break;
                    case 7:
                        buscarSeriesPorCategoria();
                        break;
                    case 8:
                        buscarSeriesPorTemporadasComAvaliacaoMinima();
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

    private void buscarSeriesPorTemporadasComAvaliacaoMinima() {
        System.out.println("Informe o total máximo de temporadas por série para filtrar: ");
        var totalTemporadas = ler.nextInt();
        ler.nextLine();
        System.out.println("Informe o valor da avaliação mínima por ´serie/temporada para filtrar as séries: ");
        var valorMinimoDaAvaliacao = ler.nextDouble();
        ler.nextLine();

        // Consulta derivada padrão JPA-Derived Queries
        //List<Serie> seriesComAvaliacaoMinimaETotalTemporadas = serieRepository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(totalTemporadas, valorMinimoDaAvaliacao);
        // Consulta padrão JPQL (Java Persistence Query Language)
        List<Serie> seriesComAvaliacaoMinimaETotalTemporadas = serieRepository.seriesFiltradaPorTotalTemporadaEAvaliacao(totalTemporadas, valorMinimoDaAvaliacao);

        if (!seriesComAvaliacaoMinimaETotalTemporadas.isEmpty()) {
            seriesComAvaliacaoMinimaETotalTemporadas.forEach(serie ->
                    System.out.println("Título da Série: " + serie.getTitulo() + " - Avaliação: " + serie.getAvaliacao())
            );
        } else {
            System.out.println("Não Foi Encontrado as Séries TOP 5!");
        }
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("\nDigite uma Categoria/Gênero: ");
        var nomeGenero = ler.nextLine();
        Categoria categoriaEmPortugues = Categoria.fromStringPortugues(nomeGenero);

        List<Serie> seriesPorCategoria = serieRepository.findByGenero(categoriaEmPortugues);

        if (!seriesPorCategoria.isEmpty()) {
            seriesPorCategoria.forEach(serie ->
                    System.out.println("Título da Série: " + serie.getTitulo() + " - Categoria: " + serie.getGenero() + " - Avaliação: " + serie.getAvaliacao())
            );
        } else {
            System.out.println("Não Foi Encontrado as Séries para o Gênero " + nomeGenero + "! ");
        }

    }

    private void buscarTop5SeriesPorOrdemDeAvaliacaoDesc() {
        List<Serie> serieTop5 = serieRepository.findTop5ByOrderByAvaliacaoDesc();

        if (!serieTop5.isEmpty()) {
            serieTop5.forEach(serie ->
                    System.out.println("Título da Série: " + serie.getTitulo() + " - Avaliação: " + serie.getAvaliacao())
            );
        } else {
            System.out.println("Não Foi Encontrado as Séries TOP 5!");
        }
    }

    private void buscarSeriesPorAtor() {
        System.out.println("Digite o nome do Ator para pesquisar as suas séries: ");
        var nomeAtor = ler.nextLine();
        System.out.println("Informe o valor da Avalição para consultar a partir deste valor: ");
        var valorDaAvaliacao = ler.nextDouble();
        ler.nextLine();

        List<Serie> seriesDoAtorAvaliadas = serieRepository.findByAtoresPrincipaisContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, valorDaAvaliacao);

        if (!seriesDoAtorAvaliadas.isEmpty()) {
            System.out.println("Séries com Ator [" + nomeAtor + "]: ");
            //System.out.println(seriesDoAtorAvaliadas);
            seriesDoAtorAvaliadas.forEach(s ->
                    System.out.println("Título da Série: " + s.getTitulo() + " - Avaliação: " + s.getAvaliacao()));
        } else {
            System.out.println("Séries não Encontrada para o Nome do Ator Infomado! ");
        }
    }

    private void buscarSeriePorTituloParcial() {
        System.out.println("Digite um Título pode ser o nome parcial: ");
        var titulo = ler.nextLine();
        Optional<Serie> serie = serieRepository.findByTituloContainsIgnoreCase(titulo);

        if (serie.isPresent()) {
            System.out.println("Dados da série -> " + serie.get());
        } else {
            System.out.println("Série não encontrado! ");
        }
    }

    private void listarSeriesConsultadas() {
        System.out.println("\n--------------------------------------------");
        System.out.println("| Listando Séries Salvas no Banco de Dados |");
        System.out.println("--------------------------------------------");

        try {
            seriesBD = serieRepository.findAll();
            seriesBD.stream()
                    .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
            //System.out.println("Pressione ENTER para voltar ao menu.");
            //ler.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao Listar Séries -> " + e.getMessage());
            throw new RuntimeException(e);
        }
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
            System.out.println("Serie adicionada com sucesso!" + serie);
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
        //DadosSerie dadosSerie = getDadosSerie();
        listarSeriesConsultadas();
        var nomeSerie = obterNomeSerieDosEpisodios();

        Optional<Serie> serie = buscarSeriePorNomeCompleto(nomeSerie);
        System.out.println("buscasEpisodiosPorSerie() -> " + serie);

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumirDadosApi.obterDados(Constantes.ENDERECO_API + CodificarURL.codificarStringParaURL(serieEncontrada.getTitulo()) + "&season=" + i + "&apikey=" + Constantes.CODIGO_API_KEY_IMDB);
                DadosTemporada dadosTemporada = converterDados.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(t.numeroDaTemporada(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        } else {
            System.out.println("\nSérie não encontrada!");
        }
    }

    private Optional<Serie> buscarSeriePorNomeCompleto(String nomeSerie) {
        try {
            return seriesBD.stream()
                    .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                    .findFirst();
        } catch (NullPointerException e) {
            System.out.println("A lista da série é nula: " + e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            return Optional.empty();
        }
    }

    private String obterNomeSerieDosEpisodios() {
        System.out.println("\nEscolha uma série e informe seu nome para pesquisar os episódios.: ");
        var nomeSerie = ler.nextLine();
        if (nomeSerie.isBlank()) {
            System.out.println("Informe um nome de série válido!");
            buscarEpisodioPorSerie();
        }
        return nomeSerie;
    }

    private void mensagemPadraoErroOpcaoMenu() {
        System.out.println("Você deve informar somente números, conforme opções do Menu. Pressione ENTER para continuar...\n");
        ler.nextLine();
    }
}
