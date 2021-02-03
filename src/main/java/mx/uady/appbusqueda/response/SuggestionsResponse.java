package mx.uady.appbusqueda.model.response;

import java.util.List;

public class SuggestionsResponse{
    private List<String> booksSuggestions;
    private List<String> textSuggestions;

    public SuggestionsResponse(List<String> booksSuggestions,List<String> textSuggestions ) {
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