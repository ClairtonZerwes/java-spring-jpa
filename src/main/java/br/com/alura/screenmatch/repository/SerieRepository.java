package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainsIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresPrincipaisContainingIgnoreCase(String atoresPrincipais);

    List<Serie> findByAtoresPrincipaisContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double valorDaAvaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoriaEmPortugues);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double valorMinimoDaAvaliacao);

    // Mesmo método da consulta derivada findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(), só que aqui utilizo JPQL(Java Persistence Query Language)
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :valorMinimoDaAvaliacao")
    List<Serie> seriesFiltradaPorTotalTemporadaEAvaliacao(int totalTemporadas, double valorMinimoDaAvaliacao);
}
