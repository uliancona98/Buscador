package mx.uady.appbusqueda.rest;

import java.util.List;
import java.io.IOException;

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
import mx.uady.appbusqueda.model.response.CorrectionsResponse;
import mx.uady.appbusqueda.model.response.SuggestionsResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import mx.uady.appbusqueda.service.SolrService;
import mx.uady.appbusqueda.model.SuggestResponse;

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
            return ResponseEntity.ok(new GenericJsonResponse(e.getMessage()));
        }

        return ResponseEntity.ok(new SearchResponse(books, booksText));
    }
    @GetMapping("/suggest")
    public ResponseEntity<Object> suggest(@RequestParam String query) {
        List<String> books;
        List<String> booksText;
        try {
            books = solrService.getSuggestionsBooksCollection(query);
            booksText = solrService.getSuggestionsTextCollection(query);

        } catch (Exception e) {
            return ResponseEntity.ok(new GenericJsonResponse(e.getMessage()));
        }
        return ResponseEntity.ok(new SuggestionsResponse(books, booksText));
    }

    @GetMapping("/corrections")
    public ResponseEntity<Object> correction(@RequestParam String query) {
        
        List<SuggestResponse> booksCorrections;
        List<SuggestResponse> textCorrections;

        try {
            booksCorrections = solrService.getCorrectionsBooksCollection(query);
            //booksCorrections = solrService.getCorrectionsTextCollection(query);
            textCorrections = solrService.getCorrectionsTextCollection(query);

        } catch (Exception e) {
            return ResponseEntity.ok(new GenericJsonResponse(e.getMessage()));
        }
        return ResponseEntity.ok(new CorrectionsResponse(booksCorrections, textCorrections));
    }




    

}