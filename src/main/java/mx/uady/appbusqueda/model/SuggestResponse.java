package mx.uady.appbusqueda.model;

import java.util.List;

public class SuggestResponse {
    private String word;
    private List<String> suggestions;

    public SuggestResponse(String word, List<String> suggestions) {
        this.word = word;
        this.suggestions = suggestions;
    }

    public String getWord(){
        return this.word;
    }

    public List<String> getSuggestions() {
        return this.suggestions;
    }

}