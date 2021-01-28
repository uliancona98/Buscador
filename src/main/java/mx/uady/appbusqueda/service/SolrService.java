package mx.uady.appbusqueda.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.uady.appbusqueda.model.BookText;
import mx.uady.appbusqueda.model.Libro;

@Service
public class SolrService {

    @Value("${solr.collection.books}")
    String booksCollection;

    @Value("${solr.collection.text}")
    String booksTextCollection;

    @Value("${solr.collection.text.highlightedField}")
    String highlightedField;

    @Autowired
    LibroService libroService;
    
    SolrClient client;

    public SolrService(@Value("${solr.url}") String solrUrl) {
        client = new HttpSolrClient.Builder(solrUrl)
        .withConnectionTimeout(10000)
        .withSocketTimeout(60000)
        .build();
    }

    public List<Libro> searchBooksCollection(String query) throws SolrServerException, IOException {
        final SolrQuery solrQuery = new SolrQuery(query);
        final QueryResponse response = client.query(booksCollection, solrQuery);

        List<Integer> bookIds = getBookIds(response.getResults());
        return getBooksFromIds(bookIds);
    }

    public List<BookText> searchBooksTextCollection(String query) throws SolrServerException, IOException {
        final SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField(highlightedField);
        final QueryResponse response = client.query(booksTextCollection, solrQuery);

        return filterHighlightedFields(response.getHighlighting());
    }

    private List<Integer> getBookIds(SolrDocumentList books) {
        List<Integer> ids = new ArrayList<>();
        for (SolrDocument book : books) {
            ids.add((Integer) book.getFirstValue("idLibro"));
        }
        return ids;
    }

    private List<Libro> getBooksFromIds(List<Integer> bookIds) {
        List<Libro> books = new ArrayList<>();

        for (Integer bookId : bookIds) {
            books.add(libroService.getLibro(bookId));
        }

        return books;
    }

    private List<BookText> filterHighlightedFields(Map<String, Map<String, List<String>>> booksText) {
        List<BookText> highlightedBooks = new ArrayList<>();

        booksText.forEach((bookId, v) -> {
            List<String> text = v.get(highlightedField);
            System.out.println(text);
            if(text == null) {
                highlightedBooks.add(new BookText(bookId, text));
            }
        });

        return highlightedBooks;
    }
}
