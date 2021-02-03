package mx.uady.appbusqueda.model.response;

import java.util.List;
import mx.uady.appbusqueda.model.SuggestResponse;

public class SuggestionsResponse{
    private List<SuggestResponse> booksSuggestions;
    private List<SuggestResponse> textSuggestions;

    public SuggestionsResponse(List<SuggestResponse> booksSuggestions,List<SuggestResponse> textSuggestions ) {
        this.booksSuggestions = booksSuggestions;
        this.textSuggestions = textSuggestions;

    }

    public List<String> getLibros() {
        return this.booksSuggestions;
    }

    public List<String> getTextoLibros() {
        return this.textSuggestions;
    }
}