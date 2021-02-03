package mx.uady.appbusqueda.model;

import java.util.List;

public class SuggestResponse {
    private String word;
    private String suggestions;

    public SuggestResponse(String word, String suggestions) {
        this.word = word;
        this.suggestions = suggestions;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSuggestions() {
        return this.suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }
}