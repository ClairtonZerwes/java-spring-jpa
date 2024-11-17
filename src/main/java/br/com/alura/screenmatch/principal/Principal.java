package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.constantes.Constantes;
import br.com.alura.screenmatch.serviceapi.ConsumirDadosApi;
import br.com.alura.screenmatch.uteis.CodificarURL;

import java.util.Scanner;

public class Principal {
    private Scanner ler = new Scanner(System.in);
    private ConsumirDadosApi consumirDadosApi = new ConsumirDadosApi();

    public void exibeMenu() {
        System.out.println("Informe o nome da série para pesquisa: ");
        var nomeSerie = ler.nextLine();
        var nomeSerieEncoded = CodificarURL.codificarParaURL(nomeSerie);
        var json = consumirDadosApi.obterDados(Constantes.ENDERECO_API + nomeSerieEncoded + Constantes.CODIGO_API_KEY_IMDB);
        System.out.println(json);
    }
}
