package mx.uady.appbusqueda.model.response;

import java.util.List;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;

public class CorrectionsResponse{
    private List<Suggestion> booksSuggestions;
    private List<Suggestion> textSuggestions;

    public CorrectionsResponse(List<Suggestion> booksSuggestions, List<Suggestion> textSuggestions) {
        this.booksSuggestions = booksSuggestions;
        this.textSuggestions = textSuggestions;

    }


}