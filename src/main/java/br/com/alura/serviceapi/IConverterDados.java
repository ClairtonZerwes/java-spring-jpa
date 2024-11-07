package br.com.alura.serviceapi;

public interface IConverterDados {

    <T> T obterDados (String json, Class<T> classe);
}
