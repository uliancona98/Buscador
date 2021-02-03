package mx.uady.appbusqueda.model.response;

import java.util.List;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import mx.uady.appbusqueda.model.SuggestResponse;

public class CorrectionsResponse{
    private List<SuggestResponse> booksSuggestions;
    private List<SuggestResponse> textSuggestions;

    public CorrectionsResponse(List<SuggestResponse> booksSuggestions, List<SuggestResponse> textSuggestions) {
        this.booksSuggestions = booksSuggestions;
        this.textSuggestions = textSuggestions;
    }
    public List<SuggestResponse> getTextSuggestions() {
        return this.textSuggestions;
    }

    public List<SuggestResponse> getBooksSuggestions() {
        return this.booksSuggestions;
    }

}