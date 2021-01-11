package mx.uady.appbusqueda.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.uady.appbusqueda.service.SolrService;

@RestController
@RequestMapping(value="/api", produces="application/json")
public class SolrRest {
    @Autowired
    SolrService solrService;

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String query) {

        String books;
        String booksText;


        try {
            books = solrService.searchBooksCollection(query);
            booksText = solrService.searchBooksTextCollection(query);
        } catch (Exception e) {
            return ResponseEntity.ok(String.format("\"error\": \"%s\"", e));
        }

        StringBuilder result = new StringBuilder();

        result.append("\"libros\": ");
        result.append(books);
        result.append(", \"texto\": ");
        result.append(booksText);

        return ResponseEntity.ok(result.toString());
    }

}
