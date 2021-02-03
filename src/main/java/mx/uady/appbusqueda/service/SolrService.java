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
import org.apache.solr.client.solrj.response.SuggesterResponse;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.uady.appbusqueda.model.BookText;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.SuggestResponse;

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

    public List<BookText> searchBooksTextCollection(String query) throws SolrServerException, IOException {
        final SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField(highlightedField);
        final QueryResponse response = client.query(booksTextCollection, solrQuery);

        return filterHighlightedFields(response.getHighlighting());
    }

    private List<BookText> filterHighlightedFields(Map<String, Map<String, List<String>>> booksText) {
        List<BookText> highlightedBooks = new ArrayList<>();

        booksText.forEach((bookId, v) -> {
            List<String> text = v.get(highlightedField);
            if(text != null) {
                highlightedBooks.add(new BookText(bookId, text));
            }
        });

        return highlightedBooks;
    }



    private List<BookText> getCorrections(Map<String, Map<String, List<String>>> booksText) {
        List<BookText> highlightedBooks = new ArrayList<>();

        booksText.forEach((bookId, v) -> {
            List<String> text = v.get(highlightedField);
            if(text != null) {
                highlightedBooks.add(new BookText(bookId, text));
            }
        });

        return highlightedBooks;
    }


    private List<SuggestResponse> getCorrections2(List<Suggestion> suggestions) {
        List<SuggestResponse> suggestionsResponse = new ArrayList<>();
        for (Suggestion suggestion : suggestions) {
            String stringAlternative = "";
            for(int i=0;i<suggestion.getAlternatives().size());
            for(String alternaviteN :suggestions.getAlternatives()){
                stringAlternative = stringAlternative+alternaviteN +" ";
            }
            suggestionsResponse.add(new SuggestResponse(suggestion.getToken(),stringAlternative));

        }
        return suggestionsResponse;
    }

    public List<String> getSuggestionsTextCollection(String query) throws SolrServerException, IOException {
        List<String> suggestions;
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/suggest");
        solrQuery.setQuery(query);
        QueryResponse response = client.query(booksTextCollection, solrQuery);
        SuggesterResponse suggesterResponse = response.getSuggesterResponse();
        suggestions = suggesterResponse.getSuggestedTerms().get("mySuggester");
        System.out.println(suggestions);
        return suggestions;
    }

    public List<String> getSuggestionsBooksCollection(String query) throws SolrServerException, IOException {
        List<String> suggestions;
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/suggest");
        solrQuery.setQuery(query);
        QueryResponse response = client.query(booksCollection, solrQuery);
        SuggesterResponse suggesterResponse = response.getSuggesterResponse();
        suggestions = suggesterResponse.getSuggestedTerms().get("mySuggester");
        System.out.println(suggestions);
        return suggestions;
    }

    public List<String,String> getCorrectionsTextCollection(String query) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/spell");
        solrQuery.setQuery(query);
        //List<Suggestion> correctionsList;
        QueryResponse response = client.query(booksTextCollection, solrQuery);
        SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
        List<String,String> array = new ArrayList();
        for (Suggestion suggestion : spellCheckResponse) {

            System.out.println("original token: " + suggestion.getToken() + " - alternatives: " + suggestion.getAlternatives());
            String stringConcatenado= "";
            for(String alternatives : suggestion.getAlternatives()){
                stringConcatenado = stringConcatenado + alternatives + " ";
            }
            array.add(suggestion.getToken(), stringConcatenado);
        }

        return array;
    }

    /*public List<SuggestResponse> getCorrectionsBooksCollection(String query) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/spell");
        solrQuery.setQuery(query);
        List<Suggestion> correctionsList;
        QueryResponse response = client.query(booksCollection, solrQuery);
        SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
        correctionsList = spellCheckResponse.getSuggestions();
        return getCorrections(correctionsList);
    }*/


}