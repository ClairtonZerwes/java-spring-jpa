package br.com.alura.screenmatch.serviceapi;

public interface IConverterDados {

    <T> T obterDados (String json, Class<T> classe);
}
