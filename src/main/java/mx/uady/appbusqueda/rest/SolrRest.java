package mx.uady.appbusqueda.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.uady.appbusqueda.model.BookText;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.response.GenericJsonResponse;
import mx.uady.appbusqueda.model.response.SearchResponse;
import mx.uady.appbusqueda.service.SolrService;

@RestController
@RequestMapping(value="/api", produces="application/json")
public class SolrRest {
    @Autowired
    SolrService solrService;

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String query) {
        List<Libro> books;
        List<BookText> booksText;

        try {
            books = solrService.searchBooksCollection(query);
            booksText = solrService.searchBooksTextCollection(query);
        } catch (Exception e) {
            return ResponseEntity.ok(new GenericJsonResponse(e.getMessage()).toString());
        }

        return ResponseEntity.ok(new SearchResponse(books, booksText));
    }

}
