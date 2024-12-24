package br.com.alura.screenmatch.model;

public enum Categoria {
    SCIFI("Sci-fi", "Ficção Científica"),
    DRAMA("Drama", "Drama"),
    COMEDIA("Comedy", "Comédia"),
    CRIME("Crime", "Crime"),
    ROMANCE("Romance", "Romance"),
    ACAO("Action", "Ação"),
    ANIMACAO("Animation", "Animação"),
    NAOINFORMADO("N/A", "Não Informado");

    private String categoriaOmdb;
    private String categoriaEmPortugues;

    Categoria(String categoriaOmbd, String categoriaEmPortugues) {
        this.categoriaOmdb = categoriaOmbd;
        this.categoriaEmPortugues = categoriaEmPortugues;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }

        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida." + text);
    }

    public static Categoria fromStringPortugues(String text) {
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaEmPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }

        throw new IllegalArgumentException("Nenhuma categoria em Português não encontrada para a string fornecida." + text);
    }
}
