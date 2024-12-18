package br.com.alura.screenmatch.model;

public enum Categoria {
    SCIFI("Sci-fi"),
    DRAMA("Drama"),
    COMEDIA("Comedy"),
    CRIME("Crime"),
    ROMANCE("Romance"),
    ACAO("Action"),
    ANIMACAO("Animation"),
    NAOINFORMADO("N/A");

    private String categoriaOmdb;

    Categoria(String categoriaOmbd) {
        this.categoriaOmdb = categoriaOmbd;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }

        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida." + text);
    }
}
