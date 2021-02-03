package mx.uady.appbusqueda.model.response;

import java.util.List;

import mx.uady.appbusqueda.model.BookText;
import mx.uady.appbusqueda.model.Libro;

public class SearchResponse{

    private List<Libro> libros;
    private List<BookText> textoLibros;

    public SearchResponse(List<Libro> libros, List<BookText> textoLibros) {
        this.libros = libros;
        this.textoLibros = textoLibros;
    }

    public List<Libro> getLibros() {
        return this.libros;
    }

    public List<BookText> getTextoLibros() {
        return this.textoLibros;
    }
}