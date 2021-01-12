package mx.uady.appbusqueda.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SolrService {

    @Value("${solr.url}")
    private String solrUrl;

    @Value("${solr.collection.books}")
    String booksCollection;

    @Value("${solr.collection.text}")
    String booksTextCollection;
    
    SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr")
    .withConnectionTimeout(10000)
    .withSocketTimeout(60000)
    .build();

    public String searchBooksCollection(String query) throws SolrServerException, IOException {
        final SolrQuery solrQuery = new SolrQuery(query);
        final QueryResponse response = client.query(booksCollection, solrQuery);

        return filterBooksFields(response.getResults());
    }

    public String searchBooksTextCollection(String query) throws SolrServerException, IOException {
        final SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("text");
        final QueryResponse response = client.query(booksTextCollection, solrQuery);

        return filterBooksTextFields(response.getHighlighting());
    }

    private String filterBooksFields(SolrDocumentList books) {
        StringBuilder response = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        
        for(SolrDocument book : books) {
            Map<String, String> filteredFields = new HashMap<>();
            int id = (Integer) book.getFirstValue("idLibro");
            filteredFields.put("id", String.valueOf(id));
            filteredFields.put("titulo", (String) book.getFirstValue("titulo"));
            filteredFields.put("autor", (String) book.getFirstValue("nombreAutor"));

            try {
                response.append(objectMapper.writeValueAsString(filteredFields));
            } catch (JsonProcessingException e) {
                return "\"error\" : \"error parsing book fields.\"";
            }
        }
        return response.toString();
    }

    private String filterBooksTextFields(Map<String, Map<String, List<String>>> booksText) {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> responseMap = new HashMap<>();
        String response;

        booksText.forEach((k,v) -> {
            String text;
            text = v.get("text").get(0);
            responseMap.put(k, text);
        });

        try {
            response = objectMapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            response = "\"error\": \"error parsing book text fields\"";
        }

        return response;
    }
}
